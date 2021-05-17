package cn.hd.test.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class DESUtils {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    private static String app_secret = "432fe1dd3a82427b8e5a1d3e4a7f649B";


    /**
     * 生成密钥
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initkey() throws Exception {

        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器
        kg.init(168);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式

        byte[] key = secretKey.getEncoded();
        BufferedOutputStream keystream =
                new BufferedOutputStream(new FileOutputStream("DESedeKey.dat"));
        keystream.write(key, 0, key.length);
        keystream.flush();
        keystream.close();

        return key;
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化Des密钥
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance(KEY_ALGORITHM);
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 欢迎密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密字符串
     *
     * @param str s
     * @param screatKey s
     * @return r
     */
    public static String encode(String str, String screatKey) {
        String result = "";
        try {
            byte[] data = encrypt(str.getBytes(), screatKey.getBytes());
            result = Base64.encode(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String encode(String str) {
        return encode(str, app_secret);
    }

    /**
     * 解密字符串
     *
     * @param str s
     * @return r
     */
    public static String decode(String str, String screatKey) {
        String result = "";
        try {
            byte[] data = Base64.decode(str);
            data = decrypt(data, screatKey.getBytes());
            result = new String(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String decode(String str) {
        return decode(str, app_secret);
    }

    /**
     * 进行加解密的测试
     * 加密后要对加密的字符串中的+/=
     * throws Exception
     */
    public static void mainR(String[] args) {
        String app_secret = "432fe1dd3a82427b8e5a1d3e4a7f649B";
        // String app_signature_input = "app_id_uid_token";
        //String true_name = "张三";
        // System.out.println("app_secret（key）:"+app_secret);
        //System.out.println("签名原文：" + true_name);
        //加密
//        String value = encode("78", app_secret);
//
//        System.out.println("加密后：" + value);

        System.out.println("解密后：" + decode("V6tetaQrL4dOR1HKU8Q8ElzpJSecmzwQ", app_secret));
    }

}
