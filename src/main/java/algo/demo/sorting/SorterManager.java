package algo.demo.sorting;

import algo.demo.map.HashMap;
import algo.demo.map.Map;
import algo.demo.sorting.sorter.*;

public class SorterManager {

    static final Map<SorterAlgorithm, ArraySorter> ALGORITHM_ARRAY_SORTER_MAP = new HashMap<>(7);

    static {
        // 注册算法
        ALGORITHM_ARRAY_SORTER_MAP.put(SorterAlgorithm.BUBBLE, new BubbleArraySorter());
        ALGORITHM_ARRAY_SORTER_MAP.put(SorterAlgorithm.SELECT, new SelectArraySorter());
        ALGORITHM_ARRAY_SORTER_MAP.put(SorterAlgorithm.INSERT, new InsertArraySorter());
        ALGORITHM_ARRAY_SORTER_MAP.put(SorterAlgorithm.SHELL, new ShellArraySorter());
        ALGORITHM_ARRAY_SORTER_MAP.put(SorterAlgorithm.QUICK, new QuickArraySorter());
        ALGORITHM_ARRAY_SORTER_MAP.put(SorterAlgorithm.MERGING, new MergingArraySorter());
    }

    public static int[] sort(SorterAlgorithm algorithm, int[] arr) {
        ArraySorter arraySorter = ALGORITHM_ARRAY_SORTER_MAP.get(algorithm);
        return arraySorter != null ? arraySorter.sort(arr) : null;
    }
}
