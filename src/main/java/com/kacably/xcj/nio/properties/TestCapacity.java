package com.kacably.xcj.nio.properties;

import com.kacably.xcj.learn.stack_1.C;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class TestCapacity {

    public static void main(String[] args) {
        byte[] byteAaaay = new byte[]{1,2,3};
        char[] charArray = new char[]{'a','b','c',1,3};

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteAaaay);
        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        System.out.println(byteBuffer.capacity());
        System.out.println(charBuffer.capacity());
    }
}
