package algo.demo;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;

public class 暴力算法查找字符串字串 {

    public static void main(String[] args) throws IOException {
        String str = request("https://www.cnblogs.com/huahuadelei/p/15892218.html");

//        String str = "aoout2jaavaac";
        String sub = "o开发博客及维护的一个过程";

//        String sub = "java";



        PrintTimeHelper.print("普通匹配算法", () -> {
            for (int i = 0; i < 100000; i++) {
                indexOf(str, sub);
            }
        });

        PrintTimeHelper.print("暴力匹配算法", () -> {
            for (int i = 0; i < 100000; i++) {
                indexOf2(str, sub);
            }
        });
    }


    /**
     * 暴力算法
     *
     * @param str
     * @param sub
     * @return
     */
    private static int indexOf2(String str, String sub) {

        int subHash = 0;
        char[] subChars = sub.toCharArray();
        for (char subChar : subChars) {
            subHash += subChar;
        }

//        System.out.println("子字符串hash ==> "+subHash);

        int strHash = 0;
        char[] strChars = str.toCharArray();
        for (int i = 0; i < subChars.length; i++) {
            strHash += strChars[i];
        }

//        System.out.println("str hash ==> "+strHash);


        outer:
        for (int i = 0; i < strChars.length - subChars.length; i++) {
            if (subHash != strHash) {
                strHash -= strChars[i];
                strHash += strChars[i + subChars.length];
                continue;
            }

            for (int j = 0; j < subChars.length; j++) {
                if (subChars[j] != strChars[i + j]) {
                    continue outer;
                }
            }
            return i;
        }

        return -1;
    }


    private static String request(String url) throws IOException {

        URL url1 = new URL(url);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        InputStream inputStream = httpURLConnection.getInputStream();


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buff)) != -1) {
            byteArrayOutputStream.write(buff, 0, len);
        }

        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    private static int indexOf(String str, String sub) {

        outer:
        for (int i = 0; i < str.length() - sub.length(); i++) {
            if (str.charAt(i) == sub.charAt(0)) {
                for (int j = 0; j < sub.length(); j++) {
                    if (sub.charAt(j) != str.charAt(i + j)) {
                        continue outer;
                    }
                }
                return i;
            }
        }

        return -1;
    }
}
