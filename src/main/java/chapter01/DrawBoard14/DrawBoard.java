package chapter01.DrawBoard14;

import javax.swing.*;
import java.awt.*;

public class DrawBoard extends JFrame {
    public static void main(String[] args) {
        DrawBoard lu = new DrawBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle(" 鼠标写字 ~ ！ ");
        this.setVisible(true);
        // 获取界面的画布
        Graphics g = this.getGraphics();
        // 创建鼠标移动监听器对象，传入画布
        DrawMove dm = new DrawMove(g);
        this.addMouseMotionListener(dm);
    }
}


