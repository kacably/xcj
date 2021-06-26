package com.kacably.xcj.learn.piped;

import java.io.PipedOutputStream;

public class ThreadWrite extends Thread {

    private WriterData writerData;

    private PipedOutputStream pipedOutputStream;

    public ThreadWrite(WriterData writerData,PipedOutputStream pipedOutputStream) {
        super();
        this.pipedOutputStream = pipedOutputStream;
        this.writerData = writerData;
    }

    @Override
    public void run() {
        writerData.writeMethod(pipedOutputStream);
    }
}
