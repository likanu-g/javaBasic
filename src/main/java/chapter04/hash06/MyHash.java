package chapter04.hash06;

//哈希表用的链表节点
class HNode {
    Item data;
    HNode next;
    HNode parent;
    public HNode(Item data) {
        this.data = data;
    }
}

// 存入的一个 k-v 对，组成一个 item
// 每个 item 存入一个链表节点再放入数组
class Item {
    int key;
    String value;
    public Item(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

// 精简哈希表实现
public class MyHash {
    // 存放数据的数组
    private final HNode[] memo = new HNode[32];
    private int count = 0;// 存入计数器
    // 存入调用
    // 1. 把 k-v 对创建成为一个 item 对象
    // 2.item 生成链表节点
    // 3. 计算 key 的哈希值在数组中的位置
    // 4. 挂链到数组中

    public void put(int key, String value) {
        Item it = new Item(key, value);
        HNode node = new HNode(it);
        int index = hashIndex(key);
        if (memo[index] != null) {
            memo[index].parent = node;
            memo[index] = node;
        } else {
            memo[index] = node;
        }
        // 5. 如果存入过多，扩容，待实现
        count++;
        int t = count / memo.length;
        if (t > 3) {
            // reHash（）；
        }
    }

    // 根据 key 取得存入的 value 值
    public String get(int key) {
        int index = hashIndex(key);
        Item item = memo[index].data;
        return item.value;
    }

    public int size() {
        return count;
    }

    // 取余法的哈希函数，得到 key 存入位置
    private int hashIndex(int key) {
        return key % memo.length;
    }
}
