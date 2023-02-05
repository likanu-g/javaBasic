package chapter01.LoginUI09;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    public static void main(String[] args) {
        LoginUI lu = new LoginUI();
        // 调用这个方法，显示界面
        lu.initUI();
    }

    public void initUI() {

        this.setSize(200, 400);
        this.setTitle(" 仿 QQ 登录界面 ");
        // 提前加上流式布局管理器
        FlowLayout ﬂ = new FlowLayout();
        this.setLayout(ﬂ);
        // 创建输入框和按钮对象
        JTextField jtf = new JTextField(8);
        JButton bu = new JButton(" 我登录 ");
        ImageIcon icon = new ImageIcon("qh.jpg");
        JLabel label = new JLabel(icon);
        this.add(label);

        // 加上输入框和按钮
        this.add(jtf);
        this.add(bu);
        this.setVisible(true);
    }
}