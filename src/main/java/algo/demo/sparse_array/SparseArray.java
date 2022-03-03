package algo.demo.sparse_array;

public class SparseArray {

    private int[][] sparseArray;

    public SparseArray(int[][] data) {
        this.sparseArray = data;
    }

    private static int[][] init(int[][] data) {
        int itemNum = getItemNum(data);

        int[][] sparseArray = new int[itemNum+1][3];
        sparseArray[0]=new int[]{data.length,data[0].length,itemNum};

        int count = 1;
        for (int r = 0; r < data.length; r++) {
            int[] datum = data[r];
            for (int c = 0; c < datum.length; c++) {
                if(datum[c] != 0){
                    sparseArray[count++]=new int[]{r,c,datum[c]};
                }
            }
        }
        return sparseArray;
    }

    private static int getItemNum(int[][] data) {
        int count = 0;
        for (int[] datum : data) {
            for (int i : datum) {
                if(i != 0){
                    count++;
                }
            }
        }

        return count;
    }

    public static SparseArray format(int[][] data){
        int[][] init = init(data);
        return new SparseArray(init);
    }

    public void print() {
        for (int i = 0; i < sparseArray.length; i++) {
            int[] data = sparseArray[i];
            for (int datum : data) {
                System.out.print(datum+"\t");
            }
            System.out.println();
        }
    }

    public int[][] toArray(){
        return sparseArray;
    }
    public int[][] toOriginArray(){
        int[][] originArray = new int[sparseArray[0][0]][sparseArray[0][1]];
        int num = sparseArray[0][2];

        for (int i = 1; i <= num; i++) {
            int[] row = sparseArray[i];
            originArray[row[0]][row[1]]=row[2];
        }

        return originArray;
    }
}
