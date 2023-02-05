package chapter03.ImageBoard01;

import javax.swing.*;
import java.awt.*;

public class ImageBoard extends JFrame {
    public static void main(String[] args) {
        ImageBoard lu = new ImageBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(400, 400);
        this.setTitle(" 图像处理 ");
        this.setVisible(true);
    }

    // 重写界面的绘制方法
    public void paint(Graphics g) {
        super.paint(g);
        for (int r = 0; r < 255; r += 1) {
            for (int b = 0; b < 255; b += 1) {
                Color color = new Color(r, 0, b);
                g.setColor(color);// 画出这个点
                g.drawLine(r + 50, b + 50, r + 50, b + 50);
            }
        }
    }

}