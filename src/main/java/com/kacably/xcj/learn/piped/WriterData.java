package com.kacably.xcj.learn.piped;

import java.io.IOException;
import java.io.PipedOutputStream;

public class WriterData {

    public void writeMethod(PipedOutputStream pipedOutputStream) {
        System.out.println("write:" + System.currentTimeMillis());
        try {
            for (int i = 0; i < 30;i++) {
                String outData = "" + (i + 1);
                pipedOutputStream.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            pipedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
