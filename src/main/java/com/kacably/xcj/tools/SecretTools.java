package com.kacably.xcj.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecretTools {

    public static String encrtption(String oldString){
        String xcjPassword = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(("xc"+oldString+"J").getBytes());
            xcjPassword = new BigInteger(messageDigest.digest()).toString(32).substring(3,19);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return xcjPassword;
    }
}
