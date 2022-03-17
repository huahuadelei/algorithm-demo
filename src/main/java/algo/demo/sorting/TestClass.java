package algo.demo.sorting;

import javax.xml.transform.Source;
import java.util.Arrays;
import java.util.Random;

public class TestClass {

    public static void main(String[] args) {
//        int[] arr = {10, 3, 1, 8, 5,2, 0,5 ,9,7};

        Random random = new Random();

        int[] arr = new int[9000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(Integer.MAX_VALUE);
        }

//        int[] c1 = arr.clone();
//        printTimes("插入排序",() -> {
//            SorterManager.sort(SorterAlgorithm.INSERT,c1);
//        });
//
//        int[] c2 = arr.clone();
//        printTimes("冒泡排序",() -> {
//            SorterManager.sort(SorterAlgorithm.BUBBLE,c2);
//        });
//
//        int[] c3 = arr.clone();
//        printTimes("选择排序",() -> {
//            SorterManager.sort(SorterAlgorithm.SELECT,c3);
//        });

        final int[] clone1 = arr.clone();

        printTimes("希尔排序",() -> {
            SorterManager.sort(SorterAlgorithm.SHELL,clone1 );
        });

        final int[] clone2 = arr.clone();
        printTimes("快速排序", () -> {
            SorterManager.sort(SorterAlgorithm.QUICK, clone2);
        });

        final int[] clone3 = arr.clone();
        printTimes("归并排序", () -> {
            SorterManager.sort(SorterAlgorithm.MERGING, clone3);
        });


//        System.out.println(Arrays.toString(clone3));
    }

    public static void printTimes(String title, Runnable runnable) {

        System.out.println("========" + title + " start=========>>>");
        long start = System.currentTimeMillis();
        System.out.println("开始：" + start);

        runnable.run();

        long end = System.currentTimeMillis();
        System.out.println("结束：" + end);
        System.out.println("共计耗时：" + (end - start));


        System.out.println("<<<========" + title + " end=========");
    }
}
