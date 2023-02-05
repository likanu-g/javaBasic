package chapter04.array04;

//定义队列类
public class MQueue {
    // 队列内部使用数组来保存数据
    private Student[] ms = new Student[0];

    // 向队列中加入一个数据对象
    public void add(Student m) {
        // 1. 先创建一个新数组，是原数组的长度 +1;
        Student[] nms = new Student[ms.length + 1];
        // 2. 把原数组中的数据复制到新数组中
        for (int i = 0; i < ms.length; i++) {
            nms[i] = ms[i];
        }
        // 3. 将数据 m 放到新数组的最后一个格子
        nms[ms.length] = m;
        // 4. 让原数组变量指向新数组
        ms = nms;
    }

    // 取出队列中 index 位置处的对象
    public Student get(int index) {
        Student m = ms[index];
        return m;
    }

    // 得到队列中元素的个数
    public int size() {
        return ms.length;
    }
}

