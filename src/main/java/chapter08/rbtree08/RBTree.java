package chapter08.rbtree08;

import static java.lang.System.out;

//红黑树构造
public class RBTree {
    // 预定义根节点变量
    // 为满足第 3 条规则：每个叶节点都带有两个空的黑色节点（被称为黑哨兵）
    private final RBNode rootTemp = new RBNode(-1);

    // 向红黑树中插入节点测试，输出测试
    public static void main(String[] args) {
        RBTree bst = new RBTree();// 创建对象
        int[] ia = new int[]{8, 2, 6, 7, 4, 3, 5, 1};
        for (int i : ia) {
            bst.addNode(i);
        }
        outTree(bst.rootTemp.getLeft());
        // RBTree bst = new RBTree();// 创建对象
        // for(int i=0;i<100;i++) {
        // Random ran=new Random();
        // int t=ran.nextInt(100000);
        // bst.addNode(t);
        // }
        // outTree(bst.rootTemp.getLeft());
    }

    // 中序遍历，" 左中右 "
    public static void outTree(RBNode node) {
        if (node != null) {
            out.print("" + node);
            outTree(node.getLeft());
            outTree(node.getRight());
        }
    }

    // 加入数据作为树上的节点
    public void addNode(int v) {
        RBNode node = new RBNode(v);
        node.setLeft(null);
        node.setRight(null);
        node.setRed(true);
        setParent(node, null);
        if (rootTemp.getLeft() == null) {
            rootTemp.setLeft(node);
            // 第 3 条规则：每个叶节点都带有两个空的黑色节点（被称为黑哨兵）
            node.setRed(false);
        } else {
            RBNode x = ﬁndParentNode(node);
            setParent(node, x);
            if (x.value > node.value) {
                x.setLeft(node);
            } else {
                x.setRight(node);
            }
            ﬁxInsert(node);
        }
    }

    // 找到节点 x 的父节点
    private RBNode ﬁndParentNode(RBNode x) {
        RBNode dataRoot = this.rootTemp.getLeft();
        RBNode child = dataRoot;
        while (child != null) {
            if (child.value == x.value) {
                return child;
            } else if (child.value > x.value) {
                dataRoot = child;
                child = child.getLeft();
            } else {
                dataRoot = child;
                child = child.getRight();
            }
        }
        return dataRoot;
    }

    // 根据插入节点左右旋来调整树：rotateLeft rotateRight
    private void ﬁxInsert(RBNode x) {
        RBNode parent = x.getParent();
        while (parent != null && parent.isRed()) {
            RBNode uncle = getUncle(x);
            if (uncle == null) {// 需要旋转
                RBNode ancestor = parent.getParent();

                // 取得爷爷节点
                if (parent == ancestor.getLeft()) {
                    boolean isRight = x == parent.getRight();
                    if (isRight) {
                        rotateLeft(parent);
                    }
                    rotateRight(ancestor);

                    if (isRight) {
                        x.setRed(false);
                        parent = null;// end loop
                    } else {
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                } else {
                    boolean isLeft = x == parent.getLeft();
                    if (isLeft) {
                        rotateRight(parent);
                    }
                    rotateLeft(ancestor);
                    if (isLeft) {
                        x.setRed(false);
                        parent = null;// end loop
                    } else {
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                }
            } else {// uncle is red parent.setRed(false); uncle.setRed(false);
                // parent.getParent().setRed(true); x=parent.getParent();
                parent = x.getParent();
            }
        }
        this.rootTemp.getLeft().makeBlack();
        this.rootTemp.setParent(null);
    }

    // 取得叔节点
    private RBNode getUncle(RBNode node) {
        RBNode parent = node.getParent();
        RBNode ancestor = parent.getParent();
        if (ancestor == null) {
            return null;
        }
        if (parent == ancestor.getLeft()) {

            return ancestor.getRight();
        } else {
            return ancestor.getLeft();
        }
    }

    // 左旋转操作
    private void rotateLeft(RBNode node) {
        RBNode right = node.getRight();
        RBNode parent = node.getParent();
        node.setRight(right.getLeft());
        setParent(right.getLeft(), node);
        right.setLeft(node);
        setParent(node, right);
        if (parent == null) {
            rootTemp.setLeft(right);
            setParent(right, null);
        } else {
            if (parent.getLeft() == node) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
            setParent(right, parent);
        }
    }

    // 右旋转操作
    private void rotateRight(RBNode node) {
        RBNode left = node.getLeft();
        RBNode parent = node.getParent();
        node.setLeft(left.getRight());
        setParent(left.getRight(), node);
        left.setRight(node);
        setParent(node, left);
        if (parent == null) {
            rootTemp.setLeft(left);
            setParent(left, null);
        } else {
            if (parent.getLeft() == node) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
            setParent(left, parent);
        }
    }

    // 设为父节点
    private void setParent(RBNode node, RBNode parent) {
        if (node != null) {
            node.setParent(parent);
            if (parent == rootTemp) {
                node.setParent(null);
            }
        }
    }
}