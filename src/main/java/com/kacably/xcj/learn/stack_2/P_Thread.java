package com.kacably.xcj.learn.stack_2;

public class P_Thread extends Thread {
    private P p;

    public P_Thread(P p){
        super();
        this.p = p;
    }


    @Override
    public void run() {
        while (true){
            try {
                p.pushService();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
