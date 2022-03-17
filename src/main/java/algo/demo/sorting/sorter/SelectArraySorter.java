package algo.demo.sorting.sorter;

/**
 * 选择排序
 * 从头开始，逐个比较
 */
public class SelectArraySorter implements ArraySorter {
    @Override
    public int[] sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        return arr;
    }
}
