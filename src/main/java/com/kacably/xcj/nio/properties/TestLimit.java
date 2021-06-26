package com.kacably.xcj.nio.properties;


import com.kacably.xcj.learn.stack_1.C;

import java.nio.CharBuffer;

/**
 * limit 代表第一个不应该读取或者写入的元素下标
 */
public class TestLimit {

    public static void main(String[] args) {
        char[] charArray = new char[]{'1','a','1',2,'c'};
        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        System.out.println(charBuffer.capacity() + "limit" + charBuffer.limit());
        charBuffer.limit(4);

        System.out.println(charBuffer.capacity() + "limit" + charBuffer.limit());
        charBuffer.put(0,'3');
        charBuffer.put(2,'2');
        charBuffer.put(4,'d');


    }
}
