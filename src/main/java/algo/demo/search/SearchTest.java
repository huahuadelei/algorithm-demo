package algo.demo.search;

import algo.demo.search.api.SearchAPI;
import algo.demo.search.api.impl.BinarySearch;
import algo.demo.search.api.impl.InsertBinarySearch;
import algo.demo.search.api.impl.SimpleSearch;
import algo.demo.search.api.impl.WhileBinarySearch;
import algo.demo.sorting.SorterAlgorithm;
import algo.demo.sorting.SorterManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class SearchTest {

    public static void main(String[] args) {

        HashSet<Integer> integers = new HashSet<>();
        Random random = new Random();

        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            do {
                arr[i] = random.nextInt(Integer.MAX_VALUE);
            }while (integers.contains(arr[i]));
            integers.add(arr[i]);
        }

        // 随机的值 需要先排序
        printTimes("快速排序",()->{
            SorterManager.sort(SorterAlgorithm.QUICK,arr);
        });

//        // 取一个索引位让下面算法进行查找
//
        int target = arr[15];
        System.out.println("target ==> "+target);
//
//        // 简单查询
//        SearchAPI searchAPI1 = new SimpleSearch();
//        printTimes("普通查找",()->{
//
//            System.out.println(searchAPI1.search(arr, target));
//        });
//
        // 简单查询
        SearchAPI binarySearch = new BinarySearch();
        printTimes("二分查找",()->{
            System.out.println(binarySearch.search(arr, target));
        });
//
        // 简单查询
        SearchAPI insertBinarySearch = new InsertBinarySearch();
        printTimes("插值法二分查找",()->{
            System.out.println(insertBinarySearch.search(arr, target));
        });
        // 简单查询
        SearchAPI whileBinarySearch = new WhileBinarySearch();
        printTimes("非递归二分查找",()->{
            System.out.println(whileBinarySearch.search(arr, target));
        });
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
