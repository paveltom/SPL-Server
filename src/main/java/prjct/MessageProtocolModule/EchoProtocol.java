package prjct.MessageProtocolModule;

import prjct.Course;
import prjct.Database;
import prjct.User;
import prjct.commands.Callback;


import java.lang.UnsupportedOperationException;

import java.util.HashMap;
import java.util.List;

public class EchoProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;
    //private HashMap<String, Callback> requestExecute;
    private User currUser = null;
    private Database database;
    private String currOpCode = "";

    @Override
    public String process(String msg) {
        try {
            shouldTerminate = false;
            database = Database.getInstance();
            String output = "";
            int indOf = msg.indexOf(" ");
            currOpCode = msg.substring(0, indOf);
            msg = msg.substring(indOf + 1);
            switch (currOpCode) {
                case "1":
                    // code block
                    break;
                case "2":
                    // code block
                    break;
                case "3":
                    // code block
                    break;
                case "4":
                    // code block
                    break;
                case "5":
                    // code block
                    break;
                case "6":
                    // code block
                    break;
                case "7":
                    // code block
                    break;
                case "8":
                    // code block
                    break;
                case "9":
                    // code block
                    break;
                case "10":
                    // code block
                    break;
                case "11":
                    // code block
                    break;
            }
            return output;
        } catch (Exception e) {
            return "ERR " + currOpCode;
        }
    }


//    private String createEcho(String message) {
//        throw new UnsupportedOperationException();
//    }

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
        /*
         no such course is exist
         no seats are available in this course
         the student does not have all the Kdam courses
         the student is not logged in)
         */
        if (currUser == null)
            return "ERROR " + currOpCode + "\n" + "(You need to login in order to perform actions...)\n";
        Course course = database.getCourseByNum(Integer.parseInt(msg.trim()));
        if (course == null)
            return "ERROR " + currOpCode + "\n" + "(There is no such course...)\n";
        List<Course> userKdamim = currUser.getCourses();
        int[] courseKdamim = course.getKdamim();
        if (courseKdamim.length != 0) {
            if (userKdamim.isEmpty())
                return "ERROR " + currOpCode + "\n" + "( " + currUser.getUsername() + " does not have all the required KDAMIM...)\n";
            else {
                for (int i = 0; i < courseKdamim.length; i++)
                    if (!userKdamim.contains(courseKdamim[i]))
                        return "ERROR " + currOpCode + "\n" + "( " + currUser.getUsername() + " does not have all the required KDAMIM...)\n";
            }
        }
        if (!course.addStudent())
            return "ERROR " + currOpCode + "\n" + "(There is no available place in this course...)\n";
        return "ACK " + currOpCode;
    }

    private String kdamcheck() {
        return UnsupportedOperationException();
    }

    private String coursestat() {
        //students sorted alphabetically:
        //        if (list.size() > 0) {
//                                                collections.sort(list, new comparator<campaign>() {
        //                @override
        //                public int compare(final campaign object1, final campaign object2) {
        //                    return object1.getname().compareto(object2.getname());
//                }
//            });
//        }
        return UnsupportedOperationException();
    }

    private String studentstat() {
        return UnsupportedOperationException();
    }

    private String isregistered() {
        return UnsupportedOperationException();
    }

    private String unregister() {
        return UnsupportedOperationException();
    }

    private String mycourses() {
        return UnsupportedOperationException();
    }

    private void createrequestexecute() {
        /*
        adminreg
        studentreg
        login
        logout
        coursereg
        kdamcheck

        coursestat:
                   students sorted alphabetically:
                                 //        if (list.size() > 0) {
//                                                collections.sort(list, new comparator<campaign>() {
                                //                @override
                                //                public int compare(final campaign object1, final campaign object2) {
                            //                    return object1.getname().compareto(object2.getname());
//                }
//            });
//        }

        studentstat
        isregistered
        unregister
        mycourses

        servertoclient:
        ack
        err
         */
    }
}
