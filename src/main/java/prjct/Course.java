package prjct;

import java.util.List;

public class Course {

    private int ID;
    private String name;
    private int[] kdamim;
    private int currStudsNum;
    private int maxStudsNum;

    public Course(int id, String name, int[] kdamim, int maxStudsNum){
        this.name = name;
        this.ID = id;
        this.kdamim = kdamim;
        this.maxStudsNum = maxStudsNum;
        this.currStudsNum = 0;
    }

    public boolean addStudent(){
        if (currStudsNum == maxStudsNum) return false;
        currStudsNum++;
        return true;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int[] getKdamim() {
        return kdamim;
    }

    public int getCurrStudsNum() {
        return currStudsNum;
    }

    public int getMaxStudsNum() {
        return maxStudsNum;
    }

    public String toString(){
        throw new UnsupportedOperationException();
    }
}
