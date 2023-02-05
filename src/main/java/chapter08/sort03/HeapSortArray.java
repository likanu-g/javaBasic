package chapter08.sort03;

import static java.lang.System.out;

//堆排序的完整代
public class HeapSortArray {
    public static void main(String[] args) {
        int[] data = {4, 6, 8, 5, 9}; // 要排序的原始数组
        heapSort(data); // 排序
        for (int i : data) {// 输出
            out.print(i + "");
        }
    }

    private static void heapSort(int[] arr) {
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);// 从下到上，从右到左调整结构
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];// 调整堆结构 + 交换堆顶元素与末尾元素
            arr[i] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, i);// 重新对堆进行调整
        }
    }

    // 堆排序调整过程——parent: 父节点，index：待排序的数据
    private static void adjustHeap(int[] arr, int parent, int index) {
        int temp = arr[parent];
        int left = 2 * parent + 1; // 左节点
        while (left < index) {
            int right = left + 1;// 右节点
            // 左节点大于左节点，则取右节点值
            if (right < index && arr[left] < arr[right]) {
                left++;
            }
            if (temp >= arr[left]) {// 父节点的值已是最大了，完成
                break;
            }
            arr[parent] = arr[left]; // 和父节点交换
            parent = left;
            left = 2 * left + 1;
        }
        arr[parent] = temp;
    }
}
