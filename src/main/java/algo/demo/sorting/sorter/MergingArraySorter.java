package algo.demo.sorting.sorter;

/**
 * 归并排序
 */
public class MergingArraySorter implements ArraySorter {

    @Override
    public int[] sort(int[] arr) {
        int[] temp = new int[arr.length];
        mergingSort(arr, 0, arr.length - 1, temp);
        return arr;
    }

    public void mergingSort(int[] arr, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }

        int mid = (right + left) / 2;

        mergingSort(arr, left, mid, temp);
        mergingSort(arr, mid + 1, right, temp);

        int i = left;
        int j = mid + 1;
        int t = left;

        // 第一波合并
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }

        // 处理左边剩余
        while (i <= mid) {
            temp[t++] = arr[i++];
        }

        // 处理右边剩余
        while (j <= right) {
            temp[t++] = arr[j++];
        }

        // 合并数组
        t = left;
        while (t <= right) {
            arr[t] = temp[t];
            t++;
        }
    }
}
