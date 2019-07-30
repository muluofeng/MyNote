package com.example.xing.string;

/**
 * @author xiexingxing
 * @Created by 2019-07-14 17:29.
 */
public class TestString {
    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        System.out.println(s1 == s2);         //false
        System.out.println(s1 == s5);         //true
        System.out.println(s1 == s6);         //false
        System.out.println(s1 == s6.intern());//true
        System.out.println(s2 == s2.intern());//false


    }
}
