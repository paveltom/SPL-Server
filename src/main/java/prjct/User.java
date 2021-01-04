package prjct;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    List<Course> courses;

    public User (String username, String password){
        this.username = username;
        this.password = password;
        this.courses = new ArrayList<Course>();
    }


    public void addCourse(Course c){
        Course tempCourse = courses.get(0);
    }
}
