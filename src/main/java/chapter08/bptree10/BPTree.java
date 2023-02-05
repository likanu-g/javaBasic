package chapter08.bptree10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//构造 B+ 树实现 BPTree 的对象
public class BPTree {
    private final int rank;// B+ 树的阶数
    private BPNode root; // 树的根节点
    private BPNode head;// 树的头节点

    public BPTree(int rank) {
        this.rank = rank;
    }// 构造时传入 B+ 树的阶数

    public BPNode getRoot() {
        return root;
    }// 对外调用取得根节点
    // 实现向树中插入一个 key 的方法以便调用
    // 如果是第一次插入，则创建 root 节点；如果是重复的 key，则结束返回
    // 然后找到最后一个 BSTree，调用 splidNode 方法，执行分裂插入

    public void insert(int key) {
        if (head == null) {// 如果是第一次插入
            List<Integer> insertKeys = new ArrayList<>();
            insertKeys.add(key);
            head = new BPNode(null, insertKeys, null, null, null);
            root = new BPNode(null, insertKeys, null, null, null);
        } else {
            BPNode node = head;
            while (node != null) {
                List<Integer> keys = node.keys;
                for (int KV : keys) {
                    if (KV == key) {// 如果插入的键已存在，则结束插入
                        System.out.println(" 暂不支持插入重复的 key " + key);
                        return;
                    }
                }
                // 如果是当前节点的最后一个，且小于下一个节点的第一个值，则分裂树
                if (node.next == null || node.next.keys.get(0) >= key) {

                    splidNode(node, key);
                    break;
                }
                node = node.next;// 执行下一层
            }
        }
    }

    // 拆分节点：当前节点数量已达 rank 限制
    // 对新插入的 key 和 BSTree 中的值进行排序比较
    // 如果本节点的 key 的数量未超过 rank 限制，则排序后直接存入 keys 队列，否则进行树的分裂
    private void splidNode(BPNode node, int key) {
        List<Integer> keys = node.keys;
        if (keys.size() == rank - 1) {
            keys.add(key);// 插入待添加的节点 Collections.sort(keys);// 排序
            int mid = keys.size() / 2;// 取出中间位置的值
            int midKey = keys.get(mid);
            // 左节点的 keys 集：实际中叶节点只存储 key
            List<Integer> leftKeys = new ArrayList<>();
            for (int i = 0; i < mid; i++) {
                leftKeys.add(keys.get(i));
            }
            List<Integer> rightKeys = new ArrayList<>();// 右节点的 keys 集
            int k;
            if (node.isLeaf()) {
                k = mid;
            } else {
                k = mid + 1;
            }
            for (int i = k; i < rank; i++) {
                rightKeys.add(keys.get(i));
            }
            // 对左右两边的元素重新排序 Collections.sort(leftKeys);
            // Collections.sort(rightKeys);
            // 构造新的树节点
            BPNode rightNode;
            BPNode leftNode;
            rightNode = new BPNode(null, rightKeys, node.next, null, node.parent);
            leftNode = new BPNode(null, leftKeys, rightNode, node.pre, node.parent);
            rightNode.pre = leftNode;// 设置新节点的父子节点关系
            if (node.nodes != null) {
                List<BPNode> nodes = node.nodes;// 取得所有的孩子节点
                List<BPNode> leftNodes = new ArrayList<>();
                List<BPNode> rightNodes = new ArrayList<>();
                for (BPNode childNode : nodes) {
                    // 取得当前孩子节点的最大键值
                    int max = childNode.keys.get(childNode.keys.size() - 1);
                    if (max < midKey) {
                        // 小于 mid 处的键的数是左节点的子节点
                        leftNodes.add(childNode);

                        childNode.parent = leftNode;
                    } else {
                        rightNodes.add(childNode); // 大于 mid 处的键的数是右节点的子节点
                        childNode.parent = rightNode;
                    }
                }
                leftNode.nodes = leftNodes;
                rightNode.nodes = rightNodes;
            }
            BPNode preNode = node.pre;// 当前节点的前节点
            if (preNode != null) {// 分裂节点后将分裂节点的前节点的后节点设置为左节点
                preNode.next = leftNode;
            }
            BPNode nextNode = node.next; // 当前节点的后节点
            // 分裂节点后将分裂节点的后节点的前节点设置为右节点
            if (nextNode != null) {
                nextNode.pre = rightNode;
            }
            if (node == head) {// 如果由头节点分裂，则分裂后左边的节点为头节点
                head = leftNode;
            }
            List<BPNode> childNodes = new ArrayList<>();// 父节点的子节点
            childNodes.add(rightNode);
            childNodes.add(leftNode);
            if (node.parent == null) {
                List<Integer> parentKey = new ArrayList<>();
                parentKey.add(midKey);
                // 构造父节点
                BPNode parentNode = new BPNode(childNodes, parentKey, null, null, null);
                rightNode.parent = parentNode;// 将子节点与父节点关联
                leftNode.parent = parentNode;
                root = parentNode;// 当前节点为根节点
            } else {
                BPNode parentNode = node.parent;
                // 将原来的子节点和新的子节点（左子节点和右子节点）合并之后与父节点关联
                // childNodes.addAll(parentNode.nodes);
                // childNodes.remove(node);// 移除正在被拆分的节点
                // parentNode.nodes=childNodes; // 将子节点与父节点关联
                // rightNode.parent=parentNode;
                leftNode.parent = parentNode;
                if (parentNode.parent == null) {
                    root = parentNode;
                }
                splidNode(parentNode, midKey);// 递归调用拆分的方法，将父节点拆分
            }
        } else {
            keys.add(key);
            Collections.sort(keys);// 排序
        }

    }

    // 打印 B+ 树：一层是打印树的节点，一层是打印节点的 key 集
    // B+ 树类似一个目录，每个节点下有多个子节点
    // 再就是叶节点的 keys 队列中，可能保存有 0~m 个 key 值
    // 所以是一个递归中的双层队列遍历过程
    public void printBtree(BPNode root) {
        if (root == this.root) {
            printKeys(root);// 打印根节点内的元素
            System.out.println();
        }
        if (root == null) {
            return;
        }
        // 遍历打印子节点的元素
        if (root.nodes != null) {
            BPNode leftNode = null;
            BPNode tmpNode = null;
            List<BPNode> childNodes = root.nodes;
            for (BPNode node : childNodes) {
                if (node.pre == null) {
                    leftNode = node;
                    tmpNode = node;
                }
            }
            while (leftNode != null) {// 打印节点中的 key 集
                printKeys(leftNode);
                System.out.print(" | ");
                leftNode = leftNode.next;
            }
            System.out.println();
            printBtree(tmpNode);
        }
    }

    // 打印树节点内的 key 集
    private void printKeys(BPNode node) {
        for (int key : node.keys) {
            System.out.print(key + "");
        }
    }
}