package prjct.MessageProtocolModule;

import prjct.Course;
import prjct.Database;
import prjct.User;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;

public class EchoProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;
    private User currUser = null;
    private Database database = Database.getInstance();
    private String currOpCode = "";
    private Object regUnregKey = new Object();

    private final AtomicBoolean loginLock = new AtomicBoolean(false);

// compare if it is false and set it to true, no synchronized needed



    @Override
    public String process(String msg) {
        //System.out.println("EchoProtocol: accepted message: " + msg);
        try {
            //System.out.println( "EchoProtocol: process: " + msg);
            shouldTerminate = false;
            database = Database.getInstance();
            //String output = "";
            int indOf = 0;
            if (msg.trim().length() == 1 || msg.trim().length() == 2)
                currOpCode = msg.trim();
            if (msg.trim().length() > 2) {
                indOf = msg.indexOf(" ");
                currOpCode = msg.substring(0, indOf);
                msg = msg.substring(indOf).trim();
            }
            switch (currOpCode) {
                case "1":
                    return adminreg(msg);
                case "2":
                    return studentreg(msg);
                case "3":
                    return login(msg);
                case "4":
                    return logout();
                case "5":
                    return coursereg(msg);
                case "6":
                    return kdamcheck(msg);
                case "7":
                    return coursestat(msg);
                case "8":
                    return studentstat(msg);
                case "9":
                    return isregistered(msg);
                case "10":
                    return unregister(msg);
                case "11":
                    return mycourses();
                default:
                    return "ERROR 13"; // No such command...";
            }
            //System.out.println("EchoProtocol: process output: " + output);
            //return output;
        } catch (Exception e) {
            //return "Exception: " + "\n(" + e.getMessage() + ")\n";
            return "ERROR 13";
        }
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    private String adminreg(String msg) {
        if (loginLock.compareAndSet(true, true))
            return "ERROR " + currOpCode;
        msg.trim();
        int indOf = msg.indexOf(" ");
        String username = msg.substring(0, indOf).trim();
        msg = msg.substring(indOf).trim();
        if (username == "" || msg == "")
            return "ERROR " + currOpCode;// + "\n";// + "(Username and Password should be entered...)\n";
        if (database.getUserByUsername(username) != null)
            return "ERROR " + currOpCode;// + "\n";// + "(This username already exist...)\n";
        User u = new User(username, msg, true);
        if (database.addUser(u))
            return "ACK " + currOpCode;// + "\n";
        return "ERROR " + currOpCode;
    }

    private String studentreg(String msg) {
        if (currUser != null)
            return "ERROR " + currOpCode;
        msg.trim();
        int indOf = msg.indexOf(" ");
        String username = msg.substring(0, indOf).trim();
        msg = msg.substring(indOf).trim();
        if (username == "" || msg == "")
            return "ERROR " + currOpCode;// + "\n";// + "(Username and Password should be entered...)\n";
        if (database.getUserByUsername(username) != null)
            return "ERROR " + currOpCode;// + "\n";// + "(This username already exist...)\n";
        User u = new User(username, msg, false);
        database.addUser(u);
        return "ACK " + currOpCode;// + "\n";
    }

    private String login(String msg) {
        msg.trim();
        int indOf = msg.indexOf(" ");
        String username = msg.substring(0, indOf).trim();
        msg = msg.substring(indOf).trim();
        if (username == "" || msg == "")
            return "ERROR " + currOpCode;// + "\n";// + "(Username and Password should be entered...)\n";
        User user = database.getUserByUsername(username);
        if (user == null)
            return "ERROR " + currOpCode;// + "\n";// + "(This username does not exist...)\n";
        if (!user.chkPass(msg))
            return "ERROR " + currOpCode;// + "\n";//+ "(Wrong Username or Password...)\n";
        if (loginLock.compareAndSet(false, true) && database.logMeIn(user)) {
            currUser = user;
            return "ACK " + currOpCode;
        } else { return "ERROR " + currOpCode; }
    }

    private String logout() {
        if (loginLock.compareAndSet(true, false)) {
            database.logMeOut(currUser);
            currUser = null;
            shouldTerminate = true;
            return "ACK " + currOpCode;
        } else { return "ERROR " + currOpCode; }
//        if (currUser == null)
//            return "ERROR " + currOpCode;// + "\n";// + "(There is no logged in user to logout...)\n";
//        currUser = null;
//        shouldTerminate = true;
//        return "ACK " + currOpCode;// + "\n";
    }

    private String coursereg(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode;// + "\n";// + "(There is no such course...)\n";
        if (currUser.isAdmin())
            return "ERROR " + currOpCode;
        BlockingQueue<Course> userKdamim = currUser.getCourses();
        int[] courseKdamim = course.getKdamim();
        if (courseKdamim.length != 0) {
            if (userKdamim.isEmpty())
                return "ERROR " + currOpCode;// + "\n";// + "( " + currUser.getUsername() + " does not have all the required KDAMIM...)\n";
            else {
                for (int i = 0; i < courseKdamim.length; i++) {
                    //if (!userKdamim.contains(courseKdamim[i]))
                    int ind = i;
                    if (!(userKdamim.stream().anyMatch(cour -> cour.getNum() == courseKdamim[ind])))
                        return "ERROR " + currOpCode;// + "\n";// + "( " + currUser.getUsername() + " does not have all the required KDAMIM...)\n";
                }
            }
        }
        if (course.getMaxStudsNum() == course.getCurrStudsNum())
            return "ERROR " + currOpCode;// + "\n";// + "(There is no available place in this course...)\n";
        synchronized (regUnregKey) {
            course.addStudent();
        }
        currUser.addCourse(course);
        return "ACK " + currOpCode;// + "\n";
    }

    private String kdamcheck(String msg) {
        if (loginLock.compareAndSet(false, false))
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode;// + "\n";// + "(There is no such course...)\n";
        String str = "ACK " + currOpCode + "\n" + course.kdamimToString();
        return str;
    }

    private String coursestat(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        if (!currUser.isAdmin())
            return "ERROR " + currOpCode;// + "\n";// + "(You have to be an admin in order to perform this action...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode;// + "\n";// + "(There is no such course...)\n";
        List<User> usersIn = database.getUsers().stream().filter(u -> u.getCourses().contains(course)).collect(Collectors.toList());
        String usersOutput = "[]";
        if (!usersIn.isEmpty()) {
            usersIn.sort(Comparator.comparing(User::getUsername));
            usersOutput = Arrays.toString(usersIn.stream().map(User::getUsername).collect(Collectors.toList()).toArray());
        }
        int diff = 0;
        synchronized (regUnregKey) {
            diff = (course.getMaxStudsNum() - course.getCurrStudsNum());
        }
        return "ACK " + currOpCode + "\n" + "Course: (" + course.getNum() + ") " + course.getName() + "\n"
                + "Seats available: " + diff + "/" + course.getMaxStudsNum() + "\n"
                + "Students Registered: " + usersOutput;// + "\n";
    }

    private String studentstat(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        if (!currUser.isAdmin())
            return "ERROR " + currOpCode;// + "\n";// + "(You have to be an admin in order to perform this action...)\n";
        User user = database.getUserByUsername(msg.trim());
        if (user == null)
            return "ERROR " + currOpCode;// + "\n";// + "(There is no such user...)\n";
        if (user.isAdmin())
            return "ERROR " + currOpCode;// + "\n";// + "(This user is an admin and not a student...)\n";

        //if (user.getCourses().isEmpty())
        //    coursesOutput = "[]";
        //else
        String coursesOutput = user.coursesToString();
        //System.out.println("EP:STUDENTSTAT_msg: " + msg);
        String str = "ACK " + currOpCode + "\n" + "Student: " + user.getUsername() + "\n"
                + "Courses: " + coursesOutput;// + "\n";
        //System.out.println("EP:STUDENTSTAT_out: " + str);
        return str;
    }

    private String isregistered(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode;// + "\n";// + "(There is no such course...)\n";
        if (currUser.isAdmin())
            return "ERROR " + currOpCode;
        if (currUser.getCourses().contains(course))
            return "ACK " + currOpCode + "\n" + "REGISTERED";//\n";
        return "ACK " + currOpCode + "\n" + "NOT REGISTERED";//\n";
    }

    private String unregister(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode;// + "\n";// + "(There is no such course...)\n";
        if (!currUser.getCourses().contains(course))
            return "ERROR " + currOpCode;// + "\n";// + "(" + currUser.getUsername() + " is not registered to this course...)\n";
        if (currUser.isAdmin())
            return "ERROR " + currOpCode;
        currUser.unregisterCourse(course);
        synchronized (regUnregKey) {
            course.removeStudent();
        }
        return "ACK " + currOpCode;// + "\n";
    }

    private String mycourses() {
        if (currUser == null)
            return "ERROR " + currOpCode;// + "\n";// + "(You need to login in order to perform actions...)\n";
        if (currUser.isAdmin())
            return "ERROR " + currOpCode;
        return "ACK " + currOpCode + "\n" + currUser.coursesToString();
    }
}
