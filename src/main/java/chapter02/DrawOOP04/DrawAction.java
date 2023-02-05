package chapter02.DrawOOP04;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//按钮的监听器实现类：获知按下了哪个按钮
public class DrawAction implements ActionListener {
    private String cmd = null; // 记录被按下的按钮上的文字信息

    public String getCMD() { // 调用这个方法，获知按下了哪个按钮
        return this.cmd;
    }

    public void actionPerformed(ActionEvent e) {
        cmd = e.getActionCommand(); // 得到单击按钮上的文字信息后，再赋值给其属性
        System.out.println(" 按下的按钮是 " + cmd);
    }
}
