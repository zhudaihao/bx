package net;


import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import encoder.BASE64Decoder;
import encoder.BASE64Encoder;


public class DesUtil {

    private final static String DES = "DES";
    
    public static void main(String[] args)
        throws Exception {
        // String data = "123456";
        // String key = "encryptKey";
        // String result = encrypt(data, key);
        // System.out.println(result);
        // System.out.println(decrypt(result, key));
        
        String security = "hYXPtcWK";// des密钥
        // String str = "123465";//
        String str =
            "{" + "\"signture\":\"601100e98bced1fdeee351e6db98addd\"," + "\"dealPwd\":\"123456\","
                + "\"phone\":\"13411874865\"," + "\"requestWhat\":\"10011101B3216FE4\"," + "\"referer\":\"\","
                + "\"pwd\":\"123456\"," + "\"userName\":\"zhangsan\"," + "\"captcha\":\"1111\"" + "}";
        System.out.println(encrypt(str, security));
        
        String en =
            "O2rmuWhOE1aTOByp6c4km3Lc7ObXrmFPIHGaTFk454SBMtRbGuHt7jkYOmL9rd1HVq45DUl+TGaH"
                + "h0sZzRIkKzB6Y5o9EhrfUvdntgOf2q2h/Ix+/VGi82dJXm4T2FX6wPiIthIg5FnVL/D8puGdTIqX"
                + "ftb72pWcH7Bb8DEvV4upl8dusFWtYqPn6LvJmcoUtOgb7y1HH875DD9x+EMgSH1WvKA8EFA1l45g"
                + "jo/SZXs7nwAEBjTpqpPAqCXjdWm0";
        System.out.println("解密:");
        System.out.println(decrypt(en, security));
    }
    
    /**
     * Description 根据键值进行加密
     * BASE64加密
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key)
        throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
    
    /**
     * 采用默认密钥加密
     * 
     * @param plainText
     * @return
     * @throws Exception (必须)
     * @see [类、类#方法、类#成员](可选)
     */
    // public static String encrypt(String plainText)
    // throws Exception {
    // return encrypt(plainText, ConstantData.DEFAULT_DES_KEY);
    // }
    
    /**
     * 根据键值进行解密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key)
        throws IOException, Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt);
    }
    
    /**
     * 采用默认密钥解密
     * 
     * @param data
     * @return
     * @throws IOException
     * @throws Exception (必须)
     * @see [类、类#方法、类#成员](可选)
     */
    // public static String decrypt(String data)
    // throws IOException, Exception {
    // return decrypt(data, ConstantData.DEFAULT_DES_KEY);
    // }
    
    /**
     * Description 根据键值进行加密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key)
        throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        
        return cipher.doFinal(data);
    }
    
    /**
     * Description 根据键值进行解密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key)
        throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        
        return cipher.doFinal(data);
    }
    
    /**
     * 配置文件加密
     * 
     * @param before
     * @return
     */
    // public static String encryptStringWithPrefix(String before) {
    // if (before == null)
    // return null;
    // try {
    // String after = ConstantData.DECRYPT_PRIFIX + encrypt(before);
    // return after;
    //
    // }
    // catch (Exception e) {
    // //e.printStackTrace();
    // Logger log = Logger.getLogger(DesUtil.class);
    // log.error("加密失败，加密字段为:" + before);
    // return before;
    // }
    // }
    
    /**
     * 判断是否为指定前缀，如果为指定前缀，解密处理
     * @return
     */
    // public static String decryptStringWithPrefix(String securityValue) {
    // if (securityValue != null && securityValue.startsWith(ConstantData.DECRYPT_PRIFIX)) {
    // String key = securityValue.substring(ConstantData.DECRYPT_PRIFIX.length(), securityValue.length());
    // try {
    // securityValue = decrypt(key);
    // }
    // catch (Exception e) {
    // //e.printStackTrace();
    // Logger log = Logger.getLogger(DesUtil.class);
    // log.error("配置文件解密失败:" + securityValue);
    // }
    // }
    //
    // return securityValue;
    // }
    public static String getKeyString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-*,.?;':|[]{}+_)(*&^%$#!~`";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}