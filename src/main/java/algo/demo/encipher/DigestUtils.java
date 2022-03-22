package algo.demo.encipher;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 消息摘要使用
 */
public class DigestUtils {

    static Encoder encoder = Encoder.BASE64_ENCODER;

    public static byte[] digest(byte[] content, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            return messageDigest.digest(content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String digestMd5(byte[] content) {
        return encoder.codec(digest(content, "MD5"));
    }

    public static String digestSHA1(byte[] content) {
        return encoder.codec(digest(content, "SHA-1"));
    }

    public static String digestSHA256(byte[] content) {
        return encoder.codec(digest(content, "SHA-256"));
    }

    public static void setEncoder(Encoder encoder) {
        DigestUtils.encoder = encoder;
    }

    public static byte[] hmac(byte[] content, byte[] key){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(128,new SecureRandom(key));
            SecretKey secretKey = keyGenerator.generateKey();

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);

            return mac.doFinal();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sHmac(byte[] content, String key){
        return encoder.codec(hmac(content,encoder.decodec(key)));
    }

    interface Encoder {

        Encoder BASE64_ENCODER = new Base64Encoder();
        Encoder HEX_ENCODER = new HexEncoder();

        String codec(byte[] content);

        byte[] decodec(String key);
    }

    static class HexEncoder implements Encoder {

        @Override
        public String codec(byte[] content) {
            StringBuilder sb  = new StringBuilder();

            for (int i = 0; i < content.length; i++) {
                byte charAt = content[i];
                String format = String.format("%02x", charAt);
                sb.append(format);
            }

            return sb.toString();
        }

        @Override
        public byte[] decodec(String key) {
            char[] chars = key.toCharArray();
            int index = 0;

            byte[] result = new byte[chars.length/2];

            for (int i = 0; i < result.length;i++) {
                result[i]= (byte)Integer.parseInt(chars[index++]+""+chars[index++],16);
            }


            return result;
        }
    }

    static class Base64Encoder implements Encoder {

        @Override
        public String codec(byte[] content) {
            return Base64.getEncoder().encodeToString(content);
        }

        @Override
        public byte[] decodec(String key) {
            return Base64.getDecoder().decode(key);
        }
    }

    public static void main(String[] args) {
        String content = "123";

        setEncoder(Encoder.HEX_ENCODER);

        System.out.println("md5 => "+digestMd5(content.getBytes()));
        System.out.println("sha1 => "+digestSHA1(content.getBytes()));
        System.out.println("sha256 => "+digestSHA256(content.getBytes()));

        System.out.println("sHmac => "+sHmac(content.getBytes(),encoder.codec(content.getBytes())));

    }
}
