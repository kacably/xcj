package com.kacably.xcj.learn.stack_2;


import java.util.ArrayList;
import java.util.List;

public class MyStack {

    private List list = new ArrayList();

    synchronized public void push() throws InterruptedException {
        if (list.size() == 1){
            System.out.println("push操作中的" + Thread.currentThread().getName() + "呈wait状态");
            this.wait();
        }
        list.add("anystring" + Math.random());
        this.notifyAll();
        System.out.println("push=" + list.size());
    }
    synchronized public String pop() throws InterruptedException {
        String returnValue = "";
        while (list.size() == 0){
            System.out.println("pop操作中的" + Thread.currentThread().getName() + "呈wait状态");
            this.wait();
        }
        returnValue = "" + list.get(0);
        list.remove(0);
        this.notifyAll();
        System.out.println(Thread.currentThread().getName()+ "pop=" + list.size());
        return returnValue;
    }
}
