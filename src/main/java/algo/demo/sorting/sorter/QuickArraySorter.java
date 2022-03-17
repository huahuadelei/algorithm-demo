package algo.demo.sorting.sorter;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickArraySorter implements ArraySorter {


    @Override
    public int[] sort(int[] arr) {
        doQuickSort(arr, 0, arr.length - 1);
        return arr;
    }

    private void doQuickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        // 基准数
        int mid = arr[(left + right) / 2];
        int l = left;
        int r = right;
        while (true) {

            // 从左到右扫描
            while (arr[l] < mid && l < r) {
                l++;
            }

            while (arr[r] > mid && l < r) {
                r--;
            }

            if (l >= r) {
                break;
            }

            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            if (arr[l] == mid) {
                r--;
            }
            if(arr[r] == mid){
                l++;
            }
        }

        doQuickSort(arr, left, r - 1);
        doQuickSort(arr, l + 1, right);
    }
}
