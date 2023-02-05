package chapter01.LoginUI11;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//创建用户输入的监听器类
public class LoginAction implements
        ActionListener {
    //当前为 null，创建后指向界面输入框
    private JTextField jtf = null;

    //创建时，输入界面类中的输入框
    public LoginAction(JTextField jtf) {
        this.jtf = jtf;
    }

    // 实现接口中的方法
    public void actionPerformed(ActionEvent e) {
        System.out.println(" 我执行啦 ");
        // 单击时就取得界面输入框的内容
        // 此时的 jtf，指向的是界面上那个输入框
        String s = jtf.getText();
        System.out.println(" 输入的是 " + s);
        if (s.equals("lanjie")) {
            // 如果输入正确，弹出新界面
            JFrame jf = new JFrame();
            jf.setTitle(" 画出新世界 ");
            jf.setSize(300, 400);
            jf.setVisible(true);
        }
    }
}
