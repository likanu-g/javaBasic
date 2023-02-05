package chapter01.oop06;

public class Master {

    static void main(String[] a) {
// 创建一个学生对象
        Student myf = new Student(" 百度 ");
// 创建一个老师对象
        Teacher te = new Teacher();
// 传入学生对象
        te.work(myf);
    }

}

