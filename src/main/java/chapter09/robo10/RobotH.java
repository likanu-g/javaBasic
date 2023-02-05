package chapter09.robo10;

//这个类中各种方法、属性用来测试反射调用
public class RobotH implements IRobot {
    private int age;
    private String name;

    public void setAge(int a) {
        this.age = a;
    }

    public String getScro(String s, int t) {
        name = s;
        if (t > s.length()) return s + " 名字成功 ";
        else return s + " 名字失败 ";
    }

    public String toString() {
        return name + "" + age;
    }
}