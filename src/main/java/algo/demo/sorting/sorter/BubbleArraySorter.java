package algo.demo.sorting.sorter;

/**
 * 冒泡排序
 * 前一个和后一个比较 大的放最后面
 * 交换次数为 length-1 即可
 * 每一次需要交换的元素数量都-1（因为最后面的已经是最大的）
 */
public class BubbleArraySorter implements ArraySorter {

    @Override
    public int[] sort(int[] arr) {

        int i = 0;

//        do {
//
//            for (int j = 0; j < arr.length - 1 - i; j++) {
//                if (arr[j] > arr[j + 1]) {
//                    int temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
//            }
//
//            i++;
//
//        } while (i != arr.length - 1);

        for(;i<arr.length-1;i++){
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return arr;

    }

}
