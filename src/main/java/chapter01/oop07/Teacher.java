package chapter01.oop07;

public class Teacher {
    public static void main(String[] args) {
        // 调用
        Person p = new Student("haha");
        Teacher t = new Teacher();
        t.work(p);
    }

    // 方法的参数是接口类型
    public void work(Person st) {
        st.study(3);
        st.friend(" 阿里星 ");
    }

}