package chapter01.LoginUI11;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    public static void main(String[] args) {
        LoginUI lu = new LoginUI();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle(" 仿 QQ 登录界面 ");
        FlowLayout ﬂ = new FlowLayout();
        this.setLayout(ﬂ);
        JButton bu1 = new JButton(" 点我！ ");
        this.add(bu1);
        JTextField jtf = new JTextField(8);
        this.add(jtf);

        // 创建监听器对象，传入输入框
        LoginAction lo = new LoginAction(jtf);
        bu1.addActionListener(lo);
        this.setVisible(true);
    }
}
