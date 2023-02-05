package chapter01.oop05;

public class Bird extends Animal {

    // 重写父类中的方法
    public void eat(String s) {
        String msg = " 小鸟吃 " + s;
        System.out.println(msg);
    }

    public void move(int len) {
        for (int i = 0; i < len; i++) {
            String msg = " 小鸟飞第 " + len + " 次 ";
            System.out.println(msg);
        }
    }
}
