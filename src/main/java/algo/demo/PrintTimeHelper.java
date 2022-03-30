package algo.demo;

public class PrintTimeHelper {

    public static void print(String title, Runnable runnable) {
        System.out.println("===========" + title + "=========>>");

        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println("执行时间 ==> " + (end - start));

        System.out.println("<<=========" + title + "==========");
    }
}
