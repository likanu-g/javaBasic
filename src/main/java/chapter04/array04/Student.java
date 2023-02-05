package chapter04.array04;

public class Student {// 学生类
    public String name;
    public int age;
    public int score;


    public Student() {
    }

    public void study(int hour) {
        score = 3 * hour;
    }

    public void show() {
        String msg = name + " 得分 " + score;
        System.out.println(msg);
    }
}
