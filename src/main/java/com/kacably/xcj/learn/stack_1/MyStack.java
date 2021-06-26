package com.kacably.xcj.learn.stack_1;


import java.util.ArrayList;
import java.util.List;

public class MyStack {

    private List list = new ArrayList();

    synchronized public void push() throws InterruptedException {
        if (list.size() == 1){
            this.wait();
        }
        list.add("anystring" + Math.random());
        this.notify();
        System.out.println("push=" + list.size());
    }
    synchronized public String pop() throws InterruptedException {
        String returnValue = "";
        if (list.size() == 0){
            System.out.println("pop操作中的" + Thread.currentThread().getName() + "呈wait状态");
            this.wait();
        }
        returnValue = "" + list.get(0);
        list.remove(0);
        this.notify();
        System.out.println("pop=" + list.size());
        return returnValue;
    }
}
