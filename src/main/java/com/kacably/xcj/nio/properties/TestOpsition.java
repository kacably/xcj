package com.kacably.xcj.nio.properties;


import java.nio.CharBuffer;

/**
 * position标识下次要读取或写入的元素位置，position不能为负，不能大于limit
 */
public class TestOpsition {

    public static void main(String[] args) {

        char[] chars = new char[]{'a','b','c','d','e'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);

        System.out.println(charBuffer.capacity() + "limit:" + charBuffer.limit() + ",position:" + charBuffer.position());

        charBuffer.position(2);
        System.out.println(charBuffer.capacity() + "limit:" + charBuffer.limit() + ",position:" + charBuffer.position());
        charBuffer.put('3');

        for (int i = 0; i < charBuffer.capacity(); i++) {
            System.out.println(charBuffer.get(i));
        }

    }
}
