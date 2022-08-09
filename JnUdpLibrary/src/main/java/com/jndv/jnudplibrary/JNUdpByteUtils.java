package com.jndv.jnudplibrary;

/**
 * Author: wangguodong
 * Date: 2022/8/8
 * QQ: 1772889689@qq.com
 * WX: gdihh8180
 * Description: udp发送消息的byte格式化
 */
public class JNUdpByteUtils {

    public static byte[] StringToByteBase(String content){
        try {
            byte[] contentByte = content.getBytes("gb2312");// 先把字符串按gb2312转成byte数组
            byte[] bytes = new byte[8 + contentByte.length];
//            03 01 00 00 03 00 00 00 65 65 65
//            bytes[0] = 0x03;
//            bytes[1] = 0x01;
//            bytes[2] = 0x00;
//            bytes[3] = 0x00;
//            byte[] cl = tenTo16Byte(contentByte.length);
//            if (cl.length>4){
//                return null;
//            }
//            for (int i = cl.length-1; i >=0 ; i--) {
//                bytes[3+cl.length-i] = cl[i];
//            }
//            if (cl.length<4){
//                for (int i = 0; i < 4 - cl.length; i++) {
//                    bytes[4+i+cl.length] = 0x00;
//                }
//            }
//            for (int i = 0; i < contentByte.length; i++) {
////            显示内容
//                bytes[i+8] = contentByte[i];
//            }

            int len = 0 ;
            bytes[len++] = 0x03;
            bytes[len++] = 0x01;
            bytes[len++] = 0x00;
            bytes[len++] = 0x00;
            byte[] cl = tenTo16Byte(contentByte.length);
            if (cl.length>4){
                return null;
            }
            for (int i = cl.length-1; i >=0 ; i--) {
                bytes[len++] = cl[i];
            }
            if (cl.length<4){
                for (int i = 0; i < 4 - cl.length; i++) {
                    bytes[len++] = 0x00;
                }
            }
            for (int i = 0; i < contentByte.length; i++) {
//            显示内容
                bytes[len++] = contentByte[i];
            }
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 10进制int值转16进制Byte值
     * @param num
     * @return
     */
    public static byte[] tenTo16Byte(int num) {
        String str = Integer.toHexString(num);
        if (num>15){
            return String2Byte(str);
        }else {
            return new byte[]{(byte) num};
        }
    }

    /**
     * String类型转十六进制byte数组
     * @param s
     * @return
     */
    public static byte[] String2Byte(String s) {
        s = s.replace(" ", "");
        s = s.replace("#", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword;
    }


}
