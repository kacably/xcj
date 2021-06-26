package com.kacably.xcj.learn.piped;

import java.io.IOException;
import java.io.PipedInputStream;

public class ReadData {

    public void readMethod(PipedInputStream pipedInputStream) {
        System.out.println("read" + System.currentTimeMillis());
       byte[] array = new byte[10];
        try {
            int readLength = pipedInputStream.read(array);
            while (readLength != -1) {
                String readStr = new String(array,0,readLength);
                System.out.print(readStr);
                readLength = pipedInputStream.read(array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pipedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
