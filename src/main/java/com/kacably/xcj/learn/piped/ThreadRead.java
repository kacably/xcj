package com.kacably.xcj.learn.piped;

import java.io.PipedInputStream;

public class ThreadRead extends Thread {

    private ReadData readData;

    private PipedInputStream pipedInputStream;

    public ThreadRead(ReadData readData,PipedInputStream pipedInputStream) {
        super();
        this.pipedInputStream = pipedInputStream;
        this.readData = readData;
    }

    @Override
    public void run() {
        readData.readMethod(pipedInputStream);
    }
}
