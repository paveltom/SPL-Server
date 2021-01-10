package prjct;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

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
        String str = Arrays.toString(courses.stream().map(course -> course.getNum()).collect(Collectors.toList()).toArray());
        str = str.replaceAll(" ", "");
        return  str;
    }

    public boolean chkPass(String pass) {
        return this.password.equals(pass);
    }

    public boolean isAdmin() {
        return adminIndctr;
    }

    public boolean unregisterCourse(Course c) {
        if (courses.contains(c)) {
            this.courses.remove(c);
            return true;
        }
        return false;
    }

    public void addCourse(Course c) {
        try {
            courses.put(c);
            courses.stream().sorted(Comparator.comparing(Course::getOrderNum));
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
