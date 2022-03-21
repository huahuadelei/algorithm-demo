package algo.demo.search.api.impl;

import algo.demo.search.api.SearchAPI;

/**
 * 简单查找
 */
public class SimpleSearch implements SearchAPI {


    @Override
    public int search(int[] arr, int target) {
        int index = -1;

        for (int i = 0; i < arr.length; i++) {
            if(arr[i]==target){
                index = i;
            }
        }
        return index;
    }
}
