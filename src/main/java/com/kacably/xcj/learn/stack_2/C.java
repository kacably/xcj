package com.kacably.xcj.learn.stack_2;

public class C {

    private MyStack myStack;

    public C(MyStack myStack){
        super();
        this.myStack = myStack;
    }

    public void popService() throws InterruptedException {
        System.out.println("pop"  + myStack.pop());
    }
}
