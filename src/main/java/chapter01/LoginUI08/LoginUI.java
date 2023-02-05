package chapter01.LoginUI08;

import javax.swing.*;

public class LoginUI extends JFrame {

    // 主函数
    public static void main(String[] a) {
        LoginUI db = new LoginUI();
        // 调用这个方法，显示界面
        db.initUI();
    }

    // 编写一个初始化方法
    public void initUI() {
        this.setSize(400, 200);
        this.setTitle(" 仿 QQ 登录界面 ");
        this.setVisible(true);
    }
}
