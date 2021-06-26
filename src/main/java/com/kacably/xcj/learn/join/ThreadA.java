package com.kacably.xcj.learn.join;

public class ThreadA extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String s = new String();
            Math.random();
        }
    }
}
