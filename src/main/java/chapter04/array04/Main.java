package chapter04.array04;

import static java.lang.System.out;

public class Main {
    // 使用队列类
    public static void main(String[] args) {
        // 创建一个队列对象
        MQueue queue = new MQueue();
        Student m = new Student();
        m.age = 20;
        m.name = " 千山雪 ";
        queue.add(m); // 加一个
        Student m2 = new Student();
        m2.age = 200;
        m2.name = " 万里云 ";
        queue.add(m2); // 再加一个
        // 队列中有几个
        int count = queue.size();
        // 把队列中每个数据都取出来
        for (int i = 0; i < count; i++) {
            Student stu = queue.get(i);
            out.println(" 名 " + stu.name);
        }
    }

}
