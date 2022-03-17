package algo.demo.sorting.sorter;

/**
 * 插入排序
 */
public class InsertArraySorter implements ArraySorter {
    @Override
    public int[] sort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];


            int j = i;
            while(j - 1 >= 0&& temp < arr[j-1]){
                arr[j] = arr[j - 1];
                j-=1;
            }
            arr[j] = temp;

        }

        return arr;
    }
}
