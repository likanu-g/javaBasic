package chapter01.oop04;

//使用学生类
//创建对象 调用方法 改变属性
public class Master {

    public static void main(String[] args) {
        // 创建两个学生对象
        Student st1 = new Student();
        st1.setName(" 关山月 ");
        st1.study(10);
        Student st2 = new Student();
        st2.setName(" 大漠风 ");
        st2.study(30);
        st1.showInfo();
        st2.showInfo();
    }

}
