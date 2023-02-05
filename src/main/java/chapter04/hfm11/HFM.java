package chapter04.hfm11;

//输出哈夫曼编码
public class HFM {
//根据权值建树，返回根节点

    public static void main(String[] args) {
        HFM hfm = new HFM();
        //1. 模拟字符及其对应权值数据
        String[] strs = {"A", "B", "C", "D", "E", "F"};
        int[] weights = {4, 6, 1, 9, 8, 2};
        //2. 创建哈夫曼树，得到根节点
        Node root = hfm.createHFM(strs, weights);
        //3. 打印，输出每个节点的编码


        hfm.printCode(root, "");
    }

    public Node createHFM(String[] strs, int[] weights) {
        // 转化成 Node 数组
        Node[] nodes = new Node[strs.length];
        for (int i = 0; i < strs.length; i++) {
            nodes[i] = new Node();
            nodes[i].weight = weights[i];
            nodes[i].str = strs[i];

        }
        while (nodes.length > 1) {
            sort(nodes); // 排序
            Node n1 = nodes[0];
            Node n2 = nodes[1];
            Node node = new Node();
            node.left = n1;
            node.right = n2;
            node.weight = n1.weight + n2.weight;    // 加入父节点的权值
            // 把 n1 和 n2 删除，加入父节点


            Node[] nodes2 = new Node[nodes.length - 1];
            for (int i = 2; i < nodes.length; i++) {
                nodes2[i - 2] = nodes[i];
            }
            nodes2[nodes2.length - 1] = node;
            nodes = nodes2;
        }
        Node root = nodes[0];
        return root;
    }

    // 冒泡排序法对 Node[] 数组进行排序
    public void sort(Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = i + 1; j < nodes.length; j++) {
                if (nodes[i].weight > nodes[j].weight) {
                    // 如果 nodes[i].data>nodes[j].data，则交换两个节点位置
                    Node temp = new Node();
                    temp = nodes[i];
                    nodes[i] = nodes[j];
                    nodes[j] = temp;
                }
            }
        }
    }

    // 打印哈夫曼树编码
    public void printCode(Node node, String code) {
        if (node != null) {
            // 先序遍历，有了条件只打印叶节点
            if (node.left == null && node.right == null) {
                String msg = node.str + " 权值 :" + node.weight + " HFM 编码：" + code;
                System.out.println(msg);
            }
            // 打印左节点编码
            printCode(node.left, code + "0");
            // 打印右节点编码
            printCode(node.right, code + "1");
        }
    }
}