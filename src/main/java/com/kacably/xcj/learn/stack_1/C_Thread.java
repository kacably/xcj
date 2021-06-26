package com.kacably.xcj.learn.stack_1;

import lombok.SneakyThrows;

public class C_Thread extends Thread {

    private C c;

    public C_Thread(C c){
        super();
        this.c = c;
    }


    @SneakyThrows
    @Override
    public void run() {
        while (true){
            c.popService();
        }
    }
}
