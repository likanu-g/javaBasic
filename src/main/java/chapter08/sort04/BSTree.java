package chapter08.sort04;

import static java.lang.System.out;

class Node { // 二叉树节点定义
    public int value;
    public Node left, right;

    public Node(int value) {
        this.value = value;
    }
}

public class BSTree {
    // 递归方法，用顺序数组中的数据，创建一个二叉搜索树
    public static Node array2Tree(int[] data, int start, int end) {
        Node root = null;
        if (end >= start) {// 二分，注意传入数组长度要 -1
            int mid = (start + end + 1) / 2;// 为什么要 +1 root=new Node(data[mid]);
            // root.left=array2Tree(data,start,mid-1);
            // root.right=array2Tree(data,mid+1,end);
        }
        return root;
    }

    // 中序输出树节点：中左右
    public static void printTree(Node root) {
        if (null != root) {
            int s = root.value;
            out.println(" 先序输出节点值： " + s);
            Node left = root.left;
            printTree(left);
            Node right = root.right;
            printTree(right);
        }
    }

    public static void main(String[] args) {
        int[] ia = new int[]{1, 2, 3, 5, 6, 9};
        Node root = array2Tree(ia, 0, ia.length - 1);
        // printTree(root);
        out.println("root.value " + root.value);// 只为验证，手动固定输出
        // out.println("root.left.value
        // "+root.left.value);
        // out.println("root.left.left.value
        // "+root.left.left.value);
        // out.println("root.left.right.value
        // "+root.left.right.value);

        out.println("root.right.value " + root.right.value);
        out.println("root.right.left.value " + root.right.left.value);
    }
}