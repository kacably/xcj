package com.kacably.xcj.learn.unsafe;



import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) {
        /*try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = Unsafe.getUnsafe();
            System.out.println(System.currentTimeMillis());
            unsafe.park(true, System.currentTimeMillis()+3000);
            System.out.println(System.currentTimeMillis());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }*/

    }
}
