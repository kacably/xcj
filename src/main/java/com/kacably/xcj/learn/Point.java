package com.kacably.xcj.learn;

import com.kacably.xcj.bean.user.UserVerifyBean;

import java.io.Serializable;
import java.util.UUID;

public class Point {

    private static <T> T[] fun1(T...arg){
        return arg;
    }

    public static void main(String[] args) {
        UserVerifyBean userVerifyBean = new UserVerifyBean();

        userVerifyBean.setUniqueId((UUID.randomUUID().toString()));
        Object[] objects = fun1(1, "2", "12", "3", "4", "34", "5", userVerifyBean);
        for (Object str:objects
             ) {
            System.out.println(str);
        }
    }

}
