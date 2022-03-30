package algo.demo.encipher;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 非对称加密
 * 公钥加密 -> 私钥解密
 * 私钥加密 -> 公钥解密
 * 私钥+明文签名 -> 公钥+明文+签名数据验签
 */
public class RSAUtils {

    /**
     *  加密
     * @param content 用于加密的明文
     * @param key 用作加密的key
     */
    public static byte[] encrypt(byte[] content,Key key){

        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,key);

            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     * @param encCont 被解密的密文数据
     * @param key 用于解密的key - 如果加密使用私钥，解密则需要用公公钥，反之
     */
    public static byte[] decrypt(byte[] encCont,Key key){

        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,key);

            return cipher.doFinal(encCont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成密钥对
     */
    public static KeyPairs generateKeyPairs() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        return new KeyPairs(publicKey,privateKey);
     }

    /**
     * 解析私钥base64编码字符串到私钥key对象
     */
     public static PrivateKey parsePrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
         byte[] key  = Base64.getDecoder().decode(privateKey);
         KeyFactory factory = KeyFactory.getInstance("RSA");
         return factory.generatePrivate(new PKCS8EncodedKeySpec(key));
     }

    /**
     * 解析公钥base64编码字符串到公钥key对象
     */
    public static PublicKey parsePublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] key  = Base64.getDecoder().decode(publicKey);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(new X509EncodedKeySpec(key));
    }

    public static byte[] generateSign(byte[] content,PrivateKey privateKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(content);
            return signature.sign();
        } catch (NoSuchAlgorithmException|InvalidKeyException ignore) {}
        return null;
    }

    public static boolean verifySign(byte[] content,byte[] signData,PublicKey publicKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(content);

            return signature.verify(signData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static class KeyPairs{
        private String publicKey;
        private String privateKey;

        public KeyPairs(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException {
        KeyPairs keyPairs = generateKeyPairs();
        String publicKey = keyPairs.getPublicKey(); // 公钥
        String privateKey = keyPairs.getPrivateKey(); // 私钥

        System.out.println(publicKey);
        System.out.println(privateKey);

        String content = "123";

        // 公私钥对象
        PrivateKey privateKeyObj = parsePrivateKey(privateKey);
        PublicKey publicKeyObj = parsePublicKey(publicKey);

        // 公钥加密
        byte[] encrypt = encrypt(content.getBytes(),publicKeyObj );
        System.out.printf("\n公钥加密后 => %s\n",Base64.getEncoder().encodeToString(encrypt));

        //私钥解密
        byte[] decrypt = decrypt(encrypt, privateKeyObj);
        System.out.println("私钥解密后 =>"+new String(decrypt));

        //------------------------------------------------------------

        // 私钥加密
        byte[] encrypt2 = encrypt(content.getBytes(), privateKeyObj);
        System.out.printf("\n私钥加密后 =>  %s\n",Base64.getEncoder().encodeToString(encrypt));

        //公钥解密
        byte[] decrypt2 = decrypt(encrypt2, publicKeyObj);
        System.out.println("公钥解密后 =>"+new String(decrypt2));



        // 签名
        String cont = "这是一个明文";
        byte[] sign = generateSign(content.getBytes(StandardCharsets.UTF_8), privateKeyObj);
        System.out.printf("\n签名数据 => %s \n",Base64.getEncoder().encodeToString(sign));

        // 验签
        boolean verifySign = verifySign(content.getBytes(StandardCharsets.UTF_8), sign, publicKeyObj);
        System.out.println("验签结果 => "+verifySign);

    }
}
