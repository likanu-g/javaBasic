package chapter08.bptree10;

import java.util.List;

//B+ 树的节点类
//1. 不像二叉树下有多个子节点
//2.B+ 树的节点是一个双向链表
public class BPNode {
    public List<BPNode> nodes; // 存放多个子节点的队列
    public List<Integer> keys;// 节点内保存数据的队列，叶节点一般为 K:V 对
    public BPNode next;// 后节点
    public BPNode pre;// 前节点
    public BPNode parent;// 父节点指向
    // 创建一个树的节点

    public BPNode(List<BPNode> nodes, List<Integer> keys, BPNode next, BPNode pre, BPNode parent) {
        this.nodes = nodes;
        this.keys = keys;
        this.next = next;
        this.parent = parent;
        this.pre = pre;
    }

    // 判断是否为叶节点：叶节点可存 key+Value，枝节点仅存 key
    boolean isLeaf() {
        return nodes == null;
    }
}
