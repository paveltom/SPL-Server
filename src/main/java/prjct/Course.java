package prjct;

import java.util.List;

public class Course {

    private int num;
    private String name;
    private int[] kdamim;
    private int currStudsNum;
    private int maxStudsNum;
    private int orderNum;

    public Course(int id, String name, int[] kdamim, int maxStudsNum, int orderNum){
        this.name = name;
        this.num = id;
        this.kdamim = kdamim;
        this.maxStudsNum = maxStudsNum;
        this.currStudsNum = 0;
        this.orderNum = orderNum;
    }

    public boolean addStudent(){
        if (currStudsNum == maxStudsNum) return false;
        currStudsNum++;
        return true;
    }

    public int getNum() {
        return num;
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

    public int getOrderNum(){
        return orderNum;
    }

    public String toString(){
        throw new UnsupportedOperationException();
    }
}
