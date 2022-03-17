package algo.demo.sorting.sorter;

/**
 * 希尔排序
 */
public class ShellArraySorter implements ArraySorter {
    @Override
    public int[] sort(int[] arr) {

        for (int n = arr.length / 2; n >= 1; n = n / 2) {

            for (int i = n; i < arr.length; i++) {
                int temp = arr[i];

                int j = i;
                while (j - n >= 0 && temp < arr[j - n]) {
                    arr[j] = arr[j - n];
                    j-=n;
                }
                arr[j] = temp;

            }

        }


        return arr;
    }
}
