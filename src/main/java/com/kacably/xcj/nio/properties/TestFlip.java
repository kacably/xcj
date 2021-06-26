package com.kacably.xcj.nio.properties;

import java.nio.CharBuffer;

public class TestFlip {

    public static void main(String[] args) {

        CharBuffer charBuffer = CharBuffer.allocate(20);
        charBuffer.put("我是中国人我在中华人民共和国");
        charBuffer.position(0);
        System.out.println(charBuffer.limit());

        for (int i = 0; i < charBuffer.limit() ; i++) {
            System.out.println(charBuffer.get());
        }

        charBuffer.clear();
        System.out.println(charBuffer.limit());
    }
}
