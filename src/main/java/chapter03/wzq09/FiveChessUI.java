package chapter03.wzq09;

import javax.swing.*;
import java.awt.*;

public class FiveChessUI extends JFrame {
    public static void main(String[] args) {
        FiveChessUI fcUI = new FiveChessUI();

        fcUI.initUI();
    }

    // 初始化五子棋窗体的方法
    public void initUI() {
        this.setTitle(" 五子棋 - 创意画板 ");
        this.setSize(600, 600);
        this.setVisible(true);
    }

    // 在重绘窗体的同时绘制棋盘
    public void paint(Graphics g) {
        super.paint(g);
        drawChessTable(g);
    }

    // 绘制棋盘的方法
    public void drawChessTable(Graphics g) {
        for (int i = 0; i < 11; i++) {// 画棋盘横线
            g.drawLine(50, 50 * (i + 1), 550, 50 * (i + 1));
        }
        for (int j = 0; j < 11; j++) {// 画棋盘竖线
            g.drawLine(50 * (j + 1), 50, 50 * (j + 1), 550);
        }
    }

}
