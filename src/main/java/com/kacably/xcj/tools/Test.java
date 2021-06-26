package com.kacably.xcj.tools;

import java.util.*;

public class Test {
    /*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Test test = new Test();
        System.out.println(test.count(sc.nextLine()));
    }*/

    public int count(String word) {
        String[] s = word.split(" ");
        if (s == null || s.length == 0) return 0;
        return s[s.length - 1].length();
    }

    public int tcf(){
        int i = 0;
        try{
            i = 1;
            return i;
        }catch (Exception e){
            return i;
        }finally {

            return 3;
        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
       /* Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] array = s.split(" ");
        //if(array.length == 1)  System.out.println(array[0]);
        for(int i = array.length - 1; i >= 0;i--){
            System.out.println(array[i]);
        }*/
        SecretTools secretTools = new SecretTools();
        Class<? extends SecretTools> aClass = secretTools.getClass();
        Class<SecretTools> testClass = SecretTools.class;
        Class<?> test = Class.forName("com.kacably.xcj.tools.SecretTools");
        System.out.println(aClass==test);
        HashMap hashMap = new HashMap();



        int i=0;
        Integer j = new Integer(0);
        System.out.println(i==j);
        System.out.println(j.equals(i));

        Test t = new Test();
        System.out.println(t.tcf());


    }

    public void myhashset(){
        HashSet hashSet = new HashSet();
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("1");



        hashSet.addAll(list);
        hashSet.stream().forEach(System.out::println);
    }




}





