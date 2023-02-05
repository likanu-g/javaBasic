package chapter01.oop04;

//定义学生类
public class Student {

    private int score = 0;// 成绩属性
    private String name;// 姓名属性

    public void setName(String s) {
        this.name = s;
    }

    public void study(int hour) {
        score = hour * 2;
    }

    public void showInfo() {
        String msg = name + " 成绩是 " + score;
        System.out.println(msg);
    }
}