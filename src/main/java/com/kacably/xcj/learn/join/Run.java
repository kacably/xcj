package com.kacably.xcj.learn.join;


public class Run {

    public static void main(String[] args) {
        ThreadB threadB = new ThreadB();
        threadB.start();
        try {
            Thread.sleep(1000);
            ThreadC threadC = new ThreadC(threadB);
            threadC.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
