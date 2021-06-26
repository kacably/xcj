package com.kacably.xcj.learn.Three;

public class Test1 {
    public static void main(String[] args) {
        A a = new A();
        Thread thread = new Thread(()->{
            for (int i = 0; i < 60; i++) {
                try {
                    a.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"A");
        Thread thread1 = new Thread(()->{
            for (int i = 0; i < 60; i++) {
                try {
                    a.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B");
        thread.start();
        thread1.start();
    }
}

class A{
    private int num=0;

    synchronized void increment() throws InterruptedException {
        if (num != 0){
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "->" + num);
        this.notify();
    }

    synchronized void decrement() throws InterruptedException {
        if (num == 0){
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "->" + num);
        this.notify();
    }
}