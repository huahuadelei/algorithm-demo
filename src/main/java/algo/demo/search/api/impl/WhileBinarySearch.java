package algo.demo.search.api.impl;

import algo.demo.search.api.SearchAPI;

public class WhileBinarySearch implements SearchAPI {
    @Override
    public int search(int[] arr, int target) {

        int l = 0;
        int r = arr.length - 1;

        int mid;

        while (l <= r) {
//            mid = (l + r) / 2; // 取中轴
            mid = l + (int)Math.floor(((target-arr[l])*1.0/(arr[r]-arr[l])) * (r-l)); // 二分查找插值法

            if (arr[mid] == target) {
                return mid;
            } else if (target > arr[mid]) {
                l = mid + 1;
            }else if(target < arr[mid]){
                r=mid-1;
            }
        }

        return -1;
    }
}
