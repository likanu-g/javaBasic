package chapter01.oop05;

public class Wolf extends Animal {

    // 重写父类中的方法
    public void eat(String s) {
        String msg = " 狼吃 " + s;
        System.out.println(msg);
    }

    public void move(int len) {
        for (int i = 0; i < len; i++) {
            String msg = " 狼奔跑 " + len + " 次 ";
            System.out.println(msg);
        }
    }
}

