package com.kacably.xcj.learn.join;

public class Test {


    public static void main(String[] args) {

        Mythread mythread = new Mythread();
        mythread.start();
        try {
            mythread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我");
    }
}
