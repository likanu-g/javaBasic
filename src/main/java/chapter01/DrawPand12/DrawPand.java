package chapter01.DrawPand12;

import javax.swing.*;

public class DrawPand extends JFrame {
    public static void main(String[] args) {
        DrawPand dp = new DrawPand();
        dp.showUI();
    }

    public void showUI() {
        this.setTitle(" 创意图形 ");
        this.setVisible(true);
        this.setSize(300, 400);
        this.setVisible(true);
        // 创建监听器类对象，再指定给界面
        DrawList dl = new DrawList();
        this.addMouseListener(dl);
    }
}
