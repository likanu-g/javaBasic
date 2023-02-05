package chapter01.oop07;

//实现 Person 的类
public class Student implements Person {
    private final String name;
    private int score;

    public Student(String n) {
        this.name = n;
    }

    //实现接口中的方法，可以为空
    public void friend(String name) {
        System.out.println(" 我有朋友 " + name);
    }

    //实现接口中的方法
    public void study(int hour) {
        score = 3 * hour;
    }

    public void show() {
        String msg = name + " 得分 " + score;
        System.out.println(msg);
    }

}
