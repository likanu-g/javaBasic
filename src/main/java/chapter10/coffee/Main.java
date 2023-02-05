package chapter10.coffee;

import java.util.concurrent.FutureTask;

public class Main {


    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 3; i++) {
            // 实现 Callable 的任务对象
            SendCoffee sender = new SendCoffee(i * 10);
            // 必须用 FutureTask 对象执行
            FutureTask<String> ft = new FutureTask(sender);
            // 执行
            ft.run();
            // 得到执行结果
            String result = ft.get();
            System.out.println("	result is " + result);
        }
    }

}
