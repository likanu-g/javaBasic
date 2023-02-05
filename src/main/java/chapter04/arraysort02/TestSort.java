package chapter04.arraysort02;

import java.util.Random;

import static java.lang.System.out;

public class TestSort {
    public static int[] create(int len) {    // 生成一个数组
        int[] base = new int[len];
        for (int i = 0; i < base.length; i++) {
            Random ran = new Random();    // 创建一个随机对象
            int value = ran.nextInt(100);
            base[i] = value;    // 给数组中指定位置赋予随机值
        }
        return base;
    }

    public static void print(int[] ia) {    // 打印出数组中的值
        for (int i = 0; i < ia.length; i++) {
            out.print("	" + ia[i]);
        }
        out.println();


    }

    public static int[] maoPao(int[] x) {    // 冒泡排序
        for (int i = 0; i < x.length; i++) {
            for (int j = i + 1; j < x.length; j++) {
                if (x[i] > x[j]) {
                    int temp = x[i];
                    x[i] = x[j];
                    x[j] = temp;
                }
            }
        }
        return x;
    }

    public static void main(String[] args) {    // 测试
        // 取得要排序的原数组
        int[] srcA = create(10);
        out.println("1. 冒泡排序前的顺序值：");
        print(srcA);
        int[] sortA = maoPao(srcA);
        out.println("  冒泡排序的结果：");
        print(sortA);
    }

}
