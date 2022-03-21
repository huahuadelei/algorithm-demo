package algo.demo.search.api.impl;

import algo.demo.search.api.SearchAPI;

/**
 * 二分查找
 */
public class InsertBinarySearch implements SearchAPI {
    @Override
    public int search(int[] arr, int target) {
        return doSearch(arr, target, 0, arr.length - 1);
    }

    private int doSearch(int[] arr, int target, int left, int right) {

        // 递归出口 未找到数据
        if (left > right) {
            return -1;
        }
        // 2,5,6,7,8,9 :: 6 :: mid =left+（5-0）*（6-2）/(9-2)
        int mid = left + (int)((right-left)*((target-arr[left])*1.0/(arr[right]-arr[left])));
        //int mid = left+(right-left)/2;
        if (arr[mid] == target) {
            return mid;
        }

        if (target > arr[mid]) {
            return doSearch(arr, target, mid + 1, right);
        }

        if (target < arr[mid]) {
            return doSearch(arr, target, left, mid - 1);
        }

        return 0;
    }
}
