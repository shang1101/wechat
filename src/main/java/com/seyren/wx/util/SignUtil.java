package com.seyren.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/*
* 请求校验工具
*
* @author seyren
* @date 2014
* */



public class SignUtil {
    //    与开发模式接口配置信息中的Token保持一置
    private static String token = "seyren";

    /*
    * 校验签名
    * @param signature 微信加密签名
    * @param timestamp 时间戳
    * @param nonce 随机数
    * @return
    * */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        //对token timestamp和nonce 按字典排序
        String paramArr[] = new String[] {token, timestamp, nonce};
        Arrays.sort(paramArr);

        //排序后的结果拼接成一个字符串
        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            //对拼接后的字符串进行sha-1加密
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte digest[] = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;

    }


    /*
    * @param byteArray
    * @return
    * */

    private static String byteToStr(byte byteArray[]) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }

        return strDigest;
    }


    /*
    * @param mByte
    * return
    * */

    private static String byteToHexStr(byte mByte) {
        char Digit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                        'B', 'C', 'D', 'E', 'F'};
        char tempArr[] = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
 }
