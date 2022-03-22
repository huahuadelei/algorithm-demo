package algo.demo.encipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * AES是目前较常用的对称加密算法
 * 其加密模式分为 AES/CBC/PKCA5Padding 和 AES/ECB/PKCA5Padding
 * 其中，AES/CBC/PKCA5Padding 更安全，但需要一个IV向量，AES/ECB/PKCA5Padding不需要IV向量且是默认的AES加密算法
 */
public class AESUtils {

    static final String AES_ALGORITHM= "AES" ; // AES/ECB/PKCS5Padding （默认） 、AES/CBC/PKCS5Padding -使用时需要传入IV向量

    public static byte[] encrypt(byte[] content, byte[] key) {
        try {
            SecretKey secretKey = generateKey(key);

            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new RuntimeException("加密失败",e);
        }
    }

    public static byte[] decrypt(byte[] encStr, byte[] key) {
        try {
            SecretKey secretKey = generateKey(key);

            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(encStr);
        } catch (Exception e) {
            throw new RuntimeException("解密失败",e);
        }
    }

    static SecretKey generateKey(byte[] key) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128,new SecureRandom(key));
        return keyGenerator.generateKey();
    }

    private static String toHexString(byte[] encrypt) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < encrypt.length; i++) {
            byte charAt = encrypt[i];

            String hexString = Integer.toHexString(charAt & 0xFF);
            if (hexString.length() == 1) {
                hexString = 0 + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    private static byte[] toBytes(String hexStr) {
        if (hexStr.length() % 2 != 0) {
            throw new IllegalArgumentException("paramter [hexStr] is not a hex string");
        }

        char[] chars = hexStr.toCharArray();
        byte[] result = new byte[chars.length / 2];

        for (int index = 0, pos = 0; index < result.length; index++) {
            result[index] = (byte) Integer.parseInt(chars[pos++] + "" + chars[pos++], 16);
        }
        return result;
    }


    public static void main(String[] args) {
        String key = "1234567";

        String content = "1234";
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

        byte[] encrypt = encrypt(contentBytes, key.getBytes());
        System.out.println("加密后 =》 "+Arrays.toString(encrypt));
        String hexString = toHexString(encrypt);
        System.out.println(hexString);

        byte[] decrypt = decrypt(encrypt, key.getBytes());

        System.out.println("解密后 =》 "+new String(decrypt,StandardCharsets.UTF_8));

//        System.out.println(Arrays.toString(toBytes(hexString)));
    }

}
