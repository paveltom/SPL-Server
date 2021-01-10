package prjct;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Course {

    private int num;
    private String name;
    private int[] kdamim;
    private AtomicInteger currStudsNum;
    private int maxStudsNum;
    private int orderNum;

    public Course(int id, String name, int[] kdamim, int maxStudsNum, int orderNum){
        this.name = name;
        this.num = id;
        this.kdamim = kdamim;
        this.maxStudsNum = maxStudsNum;
        this.currStudsNum = new AtomicInteger(0);
        this.orderNum = orderNum;
    }

    public boolean addStudent(){
        if (currStudsNum.get() == maxStudsNum) return false;
        currStudsNum.incrementAndGet();
        return true;
    }

    public boolean removeStudent() {
        if (currStudsNum.get() == 0)
            return false;
        currStudsNum.decrementAndGet();
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

    public String kdamimToString(){
        String output = "[";
        for (int i = 0; i < kdamim.length; i++){
            output = output + kdamim[i];
            if (i != kdamim.length - 1)
                output = output + ",";
        }
        output = output + "]";
        return output;
    }

    public int getCurrStudsNum() {
        return currStudsNum.get();
    }

    public int getMaxStudsNum() {
        return maxStudsNum;
    }

    public int getOrderNum(){
        return orderNum;
    }

    public String toString(){
        return num + "|" + name + "|" + Arrays.toString(kdamim) + "|" + maxStudsNum;
    }
}
