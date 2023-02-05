package chapter01.LoginUI10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//首先，编写按钮的监听器实现类
public class LoginAction implements ActionListener {
    private int count = 0;

    // 实现接口中的方法
    // 当动作发生时，执行这个方法
    public void actionPerformed(ActionEvent e) {
        count++;
        System.out.println("OK " + count);
    }

}
