package chapter04.arraysort02;

import static java.lang.System.out;


public class QuickSort {
    // 快排：left: 起点 0，right：最后位置
    public static void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }
        int base = arr[left];// 取第一个数作为基准数 base
        int i = left, j = right;
        while (i != j) {
            // 从右边开始往左找，直到找到比 base 值小的数
            while (arr[j] >= base && i < j) {

                j--;
            }
            // 再从左往右边找，直到找到比 base 值大的数
            while (arr[i] <= base && i < j) {
                i++;
            }
            // 上面的循环结束表示找到了或者 (i>=j) 了，交换两个数在数组中的位置
            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        // 将基准数放到中间的位置（基准数归位）
        arr[left] = arr[i];
        arr[i] = base;
        // 递归，继续向基准的左右两边执行和上面同样的操作
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    public static void main(String[] args) {
        int[] ia = new int[]{7, 1, 5, 4, 2, 3, 6, 9, 8};
        quickSort(ia, 0, ia.length - 1);
        // 快速排序
        for (int i = 0; i < ia.length; i++) {
            out.print(" " + ia[i]);
        }
    }
}
