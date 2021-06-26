package com.kacably.xcj.learn.join;

public class ThreadB extends Thread {
    @Override
    public void run() {
        System.out.println("bbb");
        ThreadA threadA = new ThreadA();
        threadA.start();
        try {
            threadA.join();
            System.out.println("B end");
        } catch (InterruptedException e) {
            System.out.println("B excaption");
            e.printStackTrace();
        }
    }
}
