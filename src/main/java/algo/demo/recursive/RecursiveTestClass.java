package algo.demo.recursive;

public class RecursiveTestClass {
    public static void main(String[] args) {

        // 递归字符串翻转
        String str = "abcdef";
        System.out.println(recursiveString(str));

        // 阶乘
        System.out.println(factorial(5));
    }

    // 递归翻转字符串
    public static String recursiveString(String str) {
        if (null == str || "".equals(str)) {
            return "";
        }

        String sub = str.substring(0, 1);
        return recursiveString(str.substring(1)) + sub;
    }

    /**
     * 阶乘
     */
    public static int factorial(int num) {
        if (num == 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }

}
