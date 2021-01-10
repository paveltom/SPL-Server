package prjct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User {

    private String username;
    private String password;
    BlockingQueue<Course> courses;
    private boolean adminIndctr;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.courses = new LinkedBlockingQueue<Course>();
        this.adminIndctr = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public BlockingQueue<Course> getCourses() {
        return courses;
    }

    public String coursesToString() {
        String output = "[";
        for (Course c : courses) {
            output = output + c.getNum() + ",";
        }
        output = output.substring(0, (output.length() - 1));
        output = output.concat("]");
        return output;
    }

    public boolean chkPass(String pass) {
        return this.password.equals(pass);
    }

    public boolean isAdmin() {
        return adminIndctr;
    }

    public void unregisterCourse(Course c) {
        if (this.courses.contains(c))
            this.courses.remove(c);
    }

    public void addCourse(Course c) {
        //int insrtIndex = 0;
        if (courses.size() > 0) {
            BlockingQueue<Course> tempList = new LinkedBlockingQueue<>();
            Iterator<Course> itr = courses.iterator();
            Course tempCourse;
            try {
                while (itr.hasNext()) {
                    tempCourse = itr.next();
                    if (tempCourse.getOrderNum() >= c.getOrderNum()) {
                        tempList.put(c);
                        tempList.put(tempCourse);
                        break;
                    }
                    tempList.put(tempCourse);
                }
                tempList.put(c);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            while (itr.hasNext()) {
                tempList.add(itr.next());
            }
            courses = new LinkedBlockingQueue<>(tempList);
        } else courses.add(c);
    }
}
