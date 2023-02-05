package chapter09.gctest;

import java.util.ArrayList;


//内存中可以创建多少个对象呢？测试如下代码：
public class GCTest {
    public static void main(String[] args) {
        class User {
            private final String name;

            public User(String name) {
                this.name = name;
            }

        }
        // 创建对象，并把对象存入内存
        ArrayList al = new ArrayList();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            User u = new User(" 第 " + i + " 个用户 ");
            al.add(u);
            if (i % 99999 == 0) {
                System.out.println(" 内存中已创建 User 对象个数 " + i);
            }
        }
    }
}
