package com.kacably.xcj.learn.join;

public class ThreadC extends Thread {

    private ThreadB threadB;

    public ThreadC(ThreadB threadB) {
        super();
        this.threadB = threadB;
    }
    @Override
    public void run() {
        System.out.println("CCC");
        threadB.interrupt();
    }
}
