package com.kacably.xcj.learn.stack_1;

public class P {
    private MyStack myStack;

    public P(MyStack myStack){
        super();
        this.myStack = myStack;
    }

    public void pushService() throws InterruptedException {
        myStack.push();
    }
}
