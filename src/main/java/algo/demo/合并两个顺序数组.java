package algo.demo;

import java.util.Arrays;

public class 合并两个顺序数组 {

    public static void main(String[] args) {

        int[] arr1 = {1, 3, 4, 6, 7};
        int[] arr2 = new int[]{2, 5, 8};

        int[] merge = merge(arr1, arr2);

        System.out.println(Arrays.toString(merge));

    }

    private static int[] merge(int[] arr1, int[] arr2) {
        int[] newInt = new int[arr1.length + arr2.length];


        int index1 = 0;
        int index2 = 0;
        int index3 = 0;

        while (index1 < arr1.length && index2 < arr2.length) {
                newInt[index3++]=arr1[index1] < arr2[index2]?arr1[index1++]:arr2[index2++];
        }

        if(index1 < arr1.length){
            System.arraycopy(arr1,index1,newInt,index3,arr1.length-index1);
        }
        if(index2 < arr2.length){
            System.arraycopy(arr2,index2,newInt,index3,arr2.length-index2);
        }

        return newInt;
    }
}
