package chapter04.hfm11;

//哈夫曼树的 Node 类
public class Node {
    Node left;
    Node right;
    //此节点的哈夫曼编码
    String code;
    //此节点的字符
    String str;
    //定义数据的权值
    int weight;
}
