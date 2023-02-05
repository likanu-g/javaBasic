package chapter01.oop06;

public class Student {
    // 学生类
    private final String name;
    private int score;

    public Student(String n) {
        this.name = n;
    }

    public void study(int hour) {
        score = 3 * hour;
    }

    public void show() {
        String msg = name + " 得分 " + score;
        System.out.println(msg);
    }
}