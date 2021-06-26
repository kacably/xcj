package com.kacably.xcj.nio.properties;


import java.nio.CharBuffer;

/**
 * 返回position到limit之间的元素个数
 */
public class TestRemaining {

    public static void main(String[] args) {

        char[] chars = new char[]{'a','b','c','d','e'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);

        System.out.println(charBuffer.remaining());
        charBuffer.position(3);
        System.out.println(charBuffer.remaining());

    }
}
