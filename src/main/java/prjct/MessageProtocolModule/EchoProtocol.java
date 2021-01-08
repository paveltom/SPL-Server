package prjct.MessageProtocolModule;

import prjct.Course;
import prjct.Database;
import prjct.User;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class EchoProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;
    private User currUser = null;
    private Database database;
    private String currOpCode = "";

    @Override
    public String process(String msg) {
        //System.out.println("EchoProtocol: accepted message: " + msg);
        try {
            //System.out.println( "EchoProtocol: process: " + msg);
            shouldTerminate = false;
            database = Database.getInstance();
            //String output = "";
            int indOf = 2;
            if (msg.trim().length() > 2)
                indOf = msg.indexOf(" ");
            currOpCode = msg.substring(0, indOf);
            msg = msg.substring(indOf).trim();
            switch (currOpCode) {
                case "01":
                    return adminreg(msg);
                case "02":
                    return studentreg(msg);
                case "03":
                    return login(msg);
                case "04":
                    return logout();
                case "05":
                    return coursereg(msg);
                case "06":
                    return kdamcheck(msg);
                case "07":
                    return coursestat(msg);
                case "08":
                    return studentstat(msg);
                case "09":
                    return isregistered(msg);
                case "10":
                    return unregister(msg);
                case "11":
                    return mycourses();
                default:
                    return "ERROR 13 No such command...";
            }
            //System.out.println("EchoProtocol: process output: " + output);
            //return output;
        } catch (Exception e) {
            return "Exception: " + "\n(" + e.getMessage() + ")\n";
        }
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    private String adminreg(String msg) {
        msg.trim();
        int indOf = msg.indexOf(" ");
        String username = msg.substring(0, indOf).trim();
        msg = msg.substring(indOf).trim();
        if (username == "" || msg == "")
            return "ERROR " + currOpCode + "\n" + "(Username and Password should be entered...)\n";
        if (database.getUserByUsername(username) != null)
            return "ERROR " + currOpCode + "\n" + "(This username already exist...)\n";
        User u = new User(username, msg, true);
        database.addUser(u);
        return "ACK " + currOpCode + "\n";
    }

    private String studentreg(String msg) {
        msg.trim();
        int indOf = msg.indexOf(" ");
        String username = msg.substring(0, indOf).trim();
        msg = msg.substring(indOf).trim();
        if (username == "" || msg == "")
            return "ERROR " + currOpCode + "\n" + "(Username and Password should be entered...)\n";
        if (database.getUserByUsername(username) != null)
            return "ERROR " + currOpCode + "\n" + "(This username already exist...)\n";
        User u = new User(username, msg, false);
        database.addUser(u);
        return "ACK " + currOpCode + "\n";
    }

    private String login(String msg) {
        msg.trim();
        int indOf = msg.indexOf(" ");
        String username = msg.substring(0, indOf).trim();
        if (currUser != null) {
            if (currUser.getUsername().equals(username))
                return "ERROR " + currOpCode + "\n" + "(User " + currUser.getUsername() + " is already logged in...)\n";
            else return "ERROR " + currOpCode + "\n" + "(Other user is already logged in...)\n";
        }
        msg = msg.substring(indOf).trim();
        if (username == "" || msg == "")
            return "ERROR " + currOpCode + "\n" + "(Username and Password should be entered...)\n";
        User user = database.getUserByUsername(username);
        if (user == null)
            return "ERROR " + currOpCode + "\n" + "(This username does not exist...)\n";
        if (!user.chkPass(msg))
            return "ERROR " + currOpCode + "\n" + "(Wrong Username or Password...)\n";
        currUser = user;
        return "ACK " + currOpCode + "\n";
    }

    private String logout() {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(There is no logged in user to logout...)\n";
        currUser = null;
        shouldTerminate = true;
        return "ACK " + currOpCode + "\n";
    }

    private String coursereg(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such course...)\n";
        BlockingQueue<Course> userKdamim = currUser.getCourses();
        int[] courseKdamim = course.getKdamim();
        if (courseKdamim.length != 0) {
            if (userKdamim.isEmpty())
                return "ERROR " + currOpCode + "\n" + "( " + currUser.getUsername() + " does not have all the required KDAMIM...)\n";
            else {
                for (int i = 0; i < courseKdamim.length; i++) {
                    //if (!userKdamim.contains(courseKdamim[i]))
                    int ind = i;
                    if (!(userKdamim.stream().anyMatch(cour -> cour.getNum() == courseKdamim[ind])))
                        return "ERROR " + currOpCode + "\n" + "( " + currUser.getUsername() + " does not have all the required KDAMIM...)\n";
                }
            }
        }
        if (course.getMaxStudsNum() == course.getCurrStudsNum())
            return "ERROR " + currOpCode + "\n" + "(There is no available place in this course...)\n";
        course.addStudent();
        currUser.addCourse(course);
        return "ACK " + currOpCode + "\n";
    }

    private String kdamcheck(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such course...)\n";
        return "ACK " + currOpCode + "\n" + course.kdamimToString() + "\n";
    }

    private String coursestat(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        if (!currUser.isAdmin())
            return "ERROR " + currOpCode + "\n" + "(You have to be an admin in order to perform this action...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such course...)\n";
        List<User> usersIn = database.getUsers().stream().filter(u -> u.getCourses().contains(course)).collect(Collectors.toList());
        String usersOutput = "[]";
        if (!usersIn.isEmpty()) {
            usersIn.sort(Comparator.comparing(User::getUsername));
            usersOutput = Arrays.toString(usersIn.stream().map(User::getUsername).collect(Collectors.toList()).toArray());
        }

        return "ACK " + currOpCode + "\n" + "Course: (" + course.getNum() + ") " + course.getName() + "\n"
                + "Seats available: " + (course.getMaxStudsNum() - course.getCurrStudsNum()) + "/" + course.getMaxStudsNum() + "\n"
                + "Students Registered: " + usersOutput + "\n";
    }

    private String studentstat(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        if (!currUser.isAdmin())
            return "ERROR " + currOpCode + "\n" + "(You have to be an admin in order to perform this action...)\n";
        User user = database.getUserByUsername(msg.trim());
        if (user == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such user...)\n";
        if (user.isAdmin())
            return "ERROR " + currOpCode + "\n" + "(This user is an admin and not a student...)\n";

        String coursesOutput;
        if (user.getCourses().isEmpty())
            coursesOutput = "[]";
        else coursesOutput = Arrays.toString(user.getCourses().toArray());
        return "ACK " + currOpCode + "\n" + "Student: " + user.getUsername() + "\n"
                + "Courses: " + coursesOutput + "\n";
    }

    private String isregistered(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such course...)\n";
        if (currUser.getCourses().contains(course))
            return "ACK " + currOpCode + "\n" + "REGISTERED\n";
        return "ACK " + currOpCode + "\n" + "NOT REGISTERED\n";
    }

    private String unregister(String msg) {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such course...)\n";
        if (!currUser.getCourses().contains(course))
            return "ERROR " + currOpCode + "\n" + "(" + currUser.getUsername() + " is not registered to this course...)\n";
        currUser.unregisterCourse(course);
        course.removeStudent();
        return "ACK " + currOpCode + "\n";
    }

    private String mycourses() {
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        return "ACK " + currOpCode + "\n" + currUser.coursesToString();
    }
}
