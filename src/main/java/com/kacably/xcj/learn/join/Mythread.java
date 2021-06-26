package com.kacably.xcj.learn.join;

public class Mythread extends Thread {

    @Override
    public void run() {
        int secondValue = (int) (Math.random() * 10000);
        System.out.println(secondValue);
        try {
            Thread.sleep(secondValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
