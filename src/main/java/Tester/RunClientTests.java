package Tester;


import java.util.concurrent.TimeUnit;

public class RunClientTests {

        public static void main(String[] args) {
//                for (int i = 0; i < 10; i++) {
                        Thread t = new Thread(new Tests());
                        t.start();
//                        try {
//                                t.join();
//                        } catch (InterruptedException exception) {
//                                exception.printStackTrace();
//                        }
               // }
        }
}