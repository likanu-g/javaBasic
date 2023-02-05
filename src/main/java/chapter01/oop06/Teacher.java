package chapter01.oop06;

public class Teacher {// 老师类

    public void work(Student st) {
        // 教学生：调用学生对象的方法
        st.study(3);
        st.show();
    }
}