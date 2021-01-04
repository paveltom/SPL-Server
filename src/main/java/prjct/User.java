package prjct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {

    private String username;
    private String password;
    List<Course> courses;
    private boolean adminIndctr;

    public User (String username, String password, boolean isAdmin ){
        this.username = username;
        this.password = password;
        this.courses = new ArrayList<Course>();
        this.adminIndctr = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String coursesToString(){
        String output = "[";
        for (Course c : courses){
            output = output + c.getNum() + ",";
        }
        output = output.substring(0, (output.length()-1));
        output = output + "]";
        return output;
    }

    public boolean chkPass(String pass){
        return this.password.equals(pass);
    }

    public boolean isAdmin() {
        return adminIndctr;
    }

    public void unregisterCourse(Course c){
        if (this.courses.contains(c))
        this.courses.remove(c);
    }

    public void addCourse(Course c){
        Iterator<Course> itr = courses.iterator();
        Course tempCourse = itr.next();
        int insrtIndex = 0;
        while(tempCourse != null && c.getOrderNum() > tempCourse.getOrderNum()){
            insrtIndex++;
            tempCourse = itr.next();
            if (tempCourse != null && c.getOrderNum() < tempCourse.getOrderNum())
                break;
        }
        courses.add(insrtIndex, c);
    }
}
