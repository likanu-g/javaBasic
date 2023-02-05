package chapter04.array01;

public class MainArray {

    public static void main(String[] args) {
        // 数组定义格式为 : 类型 [] 数组变量名 =new 类型 [ 长度 ];
        int[] ia = new int[10];
        for (int i = 0; i < ia.length; i++) {
            // 给数组中每个元素赋值
            ia[i] = i * 100;
        }
        int[] tem = new int[11];
        for (int i = 0; i < ia.length; i++) {
            tem[i] = ia[i];
            tem[ia.length] = 12;// 插入数组末尾的值
        }
        ia = tem;// 让原数组指向新的数组
        int len = ia.length;
        System.out.println("ia  的数组已变化，最后的元素是  " + ia[ia.length - 1]);
    }

}
