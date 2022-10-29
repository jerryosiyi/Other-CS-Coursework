public class Sorting {      // if you need any variables, add them here

    public static void insertionSort (int[][] data) {  // to complete
        int rows = data.length;

        for (int i = 1; i < rows; i++){
            int[] key = data[i];
            int index = i - 1;
            while(index >= 0 && compareLines(key, data[index]) < 0){
                data[index + 1] = data[index];
                index = index - 1;
            }
            data[index + 1] = key;
        }
    }

    public static int[][] mergeSort (int[][] data) {  // to complete
        if (data.length == 1)
            return data;
        int rows = data.length/2,cols = data[0].length;
        int [][][] lists = splitArray(data, rows, cols);

        return merge(mergeSort(lists[0]),mergeSort(lists[1]), cols);
    }

    private static int[][] merge(int[][] list1, int[][] list2, int cols) {
        int j = 0,indexOne = 0,indexTwo = 0;
        int[][] result = new int[list1.length + list2.length][cols];

        while (indexOne < list1.length && indexTwo < list2.length) {
            result[j++] = compareLines(list1[indexOne], list2[indexTwo]) <= 0 ? list1[indexOne++] : list2[indexTwo++];
        }

        while (indexOne < list1.length)
            result[j++] = list1[indexOne++];
        while (indexTwo < list2.length)
            result[j++] = list2[indexTwo++];
        return result;
    }

    public static int[][] hybridSort (int[][] data,int threshold) {  // to complete
        if (data.length <= threshold){
            insertionSort(data);
            return data;
        }
        int rows = (data.length + 1)/2, cols = data[0].length;
        int [][][] lists = splitArray(data, rows, cols);
        return merge(hybridSort(lists[0], threshold), hybridSort(lists[1], threshold), cols);
    }

    private static int[][][] splitArray(int[][] data, int rows, int cols){
        int[][] list1 = new int[rows][cols];
        int[][] list2 = new int[data.length - rows][cols];

        for (int i = 0; i < rows; i++)
            System.arraycopy(data[i], 0, list1[i], 0, cols);
        for (int i = rows; i < data.length ; i++) {
            System.arraycopy(data[i], 0, list2[i - rows], 0, cols);
        }

        return new int[][][]{list1, list2};
    }

    private static int compareLines(int[] a, int[] b) {  // You may make this public if you wish
        int n=a.length;
        if (n != b.length)
            return (a[b.length-1]+b[a.length-1]);  // this gives an error
        int i=0;
        while (i<n && a[i]==b[i])
            i++;   // skip equal elements
        if (i==n)
            return 0;
        if (a[i]<b[i])
            return -1;
        else return 1;
    }       // can add extra functions here

}