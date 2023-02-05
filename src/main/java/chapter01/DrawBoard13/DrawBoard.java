package chapter01.DrawBoard13;

import javax.swing.*;
import java.awt.*;

public class DrawBoard extends JFrame {
    public static void main(String[] args) {
        DrawBoard lu = new DrawBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle(" 鼠标点、放棋子 ~ ！ ");
        this.setVisible(true);
        // 获取界面的画布
        Graphics g = this.getGraphics();
        // 创建鼠标监听器对象，传入画布
        DrawList dl = new DrawList(g);
        this.addMouseListener(dl);
    }
}