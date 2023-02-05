package chapter10.sample;

import java.util.LinkedList;
import java.util.List;

//创建队列对象
//启动这生产者，消费者两个线程，观察它们的输出结果。
public class Mian {

    public static void main(String[] args) {
        List<Coffe> foos = new LinkedList();// 生产者和消费者共享的队列
        // 启动生产消费者线程
        TCustomer tc = new TCustomer();
        tc.foos = foos;
        tc.start();

        TProduce tp = new TProduce();
        tp.foos = foos;
        tp.start();
    }

}
