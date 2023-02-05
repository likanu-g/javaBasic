package chapter04.link05;

//用链表实现一个队列
public class LinkQueue {
    private MyLinkNode root; // 队列内部链表的根节点
    private MyLinkNode last; // 用以标记最后一个节点
    private int count; // 计数器，累计加节点数据的个数

    // 向队列中加入一个元素
    public void add(Object o) {
        if (root == null) {// 第一次
            root = new MyLinkNode();
            root.data = o;
            last = root;
        } else {
            MyLinkNode next = new MyLinkNode();
            next.data = o;
            last.next = next;
            last = next;
        }
    }

    // 取得队列中指定位置的数据
    public Object get(int index) {
        int count = 0;
        MyLinkNode tempNode = new MyLinkNode();
        tempNode = root;
        while (count != index) {
            tempNode = tempNode.next;
            count++;
        }
        return tempNode;
    }

    // 队列中元素的个数
    public int size() {
        return this.count;
    }
}