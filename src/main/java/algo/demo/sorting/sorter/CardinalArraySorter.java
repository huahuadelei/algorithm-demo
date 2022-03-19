package algo.demo.sorting.sorter;

import java.util.Arrays;

/**
 * 基数排序
 */
public class CardinalArraySorter implements ArraySorter {

    @Override
    public int[] sort(int[] arr) {

        int[][] bucket = new int[10][arr.length];
        int[] count = new int[10];

        //取最大值
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        int length = String.valueOf(max).length();

        for (int i = 0, g = 1; i < length; i++, g *= 10) {
            for (int j = 0; j < arr.length; j++) {
                int num = arr[j] / g % 10;

                bucket[num][count[num]++]=arr[j];
            }

            int arrIndex = 0;
            for (int c = 0; c < count.length; c++) {
                if(count[c]==0){
                    continue;
                }

                for (int index = 0; index < count[c]; index++) {
                    arr[arrIndex++] = bucket[c][index];
                }

                count[c]=0;
            }

        }


        return new int[0];
    }
}
