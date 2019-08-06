package com.example.xing.common;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author xiexingxing
 * @Created by 2019-01-12 5:28 PM.
 */
public class Md5DigestUtils {

    public static final int SALT_LENGTH = 20;


    /**
     * 生成加密的字符串
     *
     * @param pass
     * @param salt
     * @return
     */
    public static String getMd5Str(String pass, String salt) {
        String code = pass + salt;
        String md5Str = "";
        byte[] digest1 = null;
        try {
            digest1 = DigestUtils.getMd5Digest().digest(code.getBytes("UTF-8"));
            String s = Hex.encodeHexString(digest1);
            byte[] digest2 = DigestUtils.getMd5Digest().digest(s.getBytes("UTF-8"));
            md5Str = Hex.encodeHexString(digest2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    /**
     * 检查app端密码是否正确,后台使用 ShiroUtils.sha256
     *
     * @param pass   明文密码
     * @param salt   盐
     * @param md5Str 加密的密码
     * @return
     */
    public static boolean checkPass(String pass, String salt, String md5Str) {
        if (pass == null || salt == null || md5Str == null) {
            return false;
        }
        String s = getMd5Str(pass, salt);
        return md5Str.equals(s) ? true : false;
    }

    /**
     * 生成盐值
     *
     * @return
     */
    public static String generatorSalt() {
        return RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
    }


    public static void main(String[] args) {
        String phone ="14739999788";
        String salt =generatorSalt();
        System.out.println(salt);
        System.out.println(getMd5Str("123",salt));
    }

}
