package com.kacably.xcj.learn.interrupt;

import java.util.concurrent.TimeUnit;

public class Interrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread sleep = new Thread(new Sleep(),"sleep");
        sleep.setDaemon(true);

        Thread busy = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                }
            }
        });
        busy.setDaemon(true);
        sleep.start();
        busy.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sleep.interrupt();
        busy.interrupt();

        System.out.println("sleep" + sleep.isInterrupted());
        System.out.println("busy" + busy.isInterrupted());

        TimeUnit.SECONDS.sleep(2);
    }

    static class Sleep implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
