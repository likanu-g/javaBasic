package chapter01.LoginUI10;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    public static void main(String[] a) {
        LoginUI lu = new LoginUI();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle(" 仿 QQ 登录界面 ");
        // 提前加上流式布局管理器
        FlowLayout ﬂ = new FlowLayout();
        this.setLayout(ﬂ);
        JButton bu1 = new JButton(" 点我！ ");
        this.add(bu1);
        // 创建监听器对象，再指定给按钮
        LoginAction lo = new LoginAction();
        bu1.addActionListener(lo);
        this.setVisible(true);

    }
}

