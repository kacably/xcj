package com.kacably.xcj.learn.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Run {

    public static void main(String[] args) {
        WriterData writerData = new WriterData();
        ReadData readData = new ReadData();

        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();

        try {
            pipedInputStream.connect(pipedOutputStream);

            ThreadRead threadRead = new ThreadRead(readData,pipedInputStream);
            threadRead.start();

            Thread.sleep(2000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ThreadWrite threadWrite = new ThreadWrite(writerData,pipedOutputStream);
        threadWrite.start();
    }
}
