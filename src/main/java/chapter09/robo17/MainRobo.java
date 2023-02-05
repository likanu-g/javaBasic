package chapter09.robo17;

import java.util.Scanner;

import static java.lang.System.out;

public class MainRobo {


    // 测试加载，转为接口调用
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            out.println("*** 测试多次加载，是否加载：");
            String input = sc.next();// 用户输入类名，全包名
            MyClassLoader loader = new MyClassLoader();// 用自定义的类加载
            Class c = loader.loadClass(input);
            Object o = c.newInstance();
            //Method m=c.getMethod("work",int.class);// 反射调用 work 方法
            //m.invoke(o,10);
            IRobo robo = (IRobo) o; // 转型为接口类型调用
            robo.work(100);
        }
    }
}
