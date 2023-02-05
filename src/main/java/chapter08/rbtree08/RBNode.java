package chapter08.rbtree08;
//红黑树节点定义

public class RBNode {
    public int value;
    private RBNode left;
    private RBNode right;
    private RBNode parent;
    private boolean red;

    // get/setter:
    public RBNode(int value) {
        this.value = value;
    }

    RBNode getLeft() {
        return left;
    }

    void setLeft(RBNode left) {
        this.left = left;
    }

    RBNode getRight() {
        return right;
    }

    void setRight(RBNode right) {
        this.right = right;
    }

    RBNode getParent() {
        return parent;
    }

    void setParent(RBNode parent) {
        this.parent = parent;
    }

    boolean isRed() {
        return red;
    }

    void setRed(boolean red) {
        this.red = red;
    }

    boolean isBlack() {
        return !red;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    void makeRed() {
        red = true;
    }

    void makeBlack() {
        red = false;
    }

    public String toString() {
        return (red == true ? " 红 " : " 黑 ") + "- 值 :" + value + "";
    }
}
