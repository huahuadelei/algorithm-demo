package algo.demo.sparse_array;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestClass {

    public static void main(String[] args) {


        int[][] data = new int[30][30];
        randData(data,2);

        System.out.println("棋盘原始数据");
        printArray(data);

        System.out.println("\n稀疏数组压缩后");
        SparseArray sparseArray = SparseArray.format(data);
        printArray(sparseArray.toArray());

        System.out.println("\n稀疏数组还原二维数组");
        printArray(sparseArray.toOriginArray());

    }

    // 随机设置坐标点
    static void randData(int[][] data, int pointNum) {
        if (data.length == 0 || data.length * data[0].length < pointNum) {
            throw new RuntimeException("数据异常");
        }

        Random random = new Random();


        Set<String> checkSet = new HashSet<>();

        for (int i = 0; i < pointNum; i++) {
            int row = random.nextInt(data.length);
            int column = random.nextInt(data[0].length);

            if (checkSet.contains(column + "," + row)) {
                i--;
                continue;
            }

            int value = random.nextInt(2) + 1;
            checkSet.add(column + "," + row);
            data[row][column]= value;
        }
    }

    // 打印数组
    static void printArray(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            int[] row = data[i];

            for (int j = 0; j < row.length; j++) {
                System.out.printf("%d\t", row[j]);
            }
            System.out.println();
        }
    }
}
