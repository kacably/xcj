package com.kacably.xcj.nio.properties;

import java.nio.ByteBuffer;

public class TestCompare {

    public static void main(String[] args) {
        byte[] byteArrayIn1 = {3,4,5};
        byte[] byteArrayIn2 = {1,2,3,104,5,6,7,8,9};

        ByteBuffer byteBuffer1 = ByteBuffer.wrap(byteArrayIn1);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byteArrayIn2);

        byteBuffer2.position(2);
        System.out.println(byteBuffer1.remaining());
        System.out.println(byteBuffer2.remaining());

        System.out.println(byteBuffer1.compareTo(byteBuffer2));

    }
}
