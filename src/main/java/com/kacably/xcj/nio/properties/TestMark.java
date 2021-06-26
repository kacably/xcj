package com.kacably.xcj.nio.properties;

import java.nio.ByteBuffer;

/**
 * 为缓冲区标记，执行reset会将position位置重置为索引，不能为负，不能大于position，如果定义之后，
 * 将position或者limit调整为小于mark，则mark丢弃，丢弃后为-1，如果未定义则调用reset会  InvalidMarkException
 */
public class TestMark {

    public static void main(String[] args) {
        byte[] bytes = new byte[]{1,2,3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        System.out.println(byteBuffer.capacity());

        byteBuffer.position(1);
        byteBuffer.mark();

        System.out.println(byteBuffer.position());

        byteBuffer.position(2);
        byteBuffer.reset();

        System.out.println(byteBuffer.position());
    }
}
