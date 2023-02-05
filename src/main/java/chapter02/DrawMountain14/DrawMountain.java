package chapter02.DrawMountain14;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//编写如下的“造山”代码：
public class DrawMountain extends JFrame {
    public static void main(String[] args) {
        DrawMountain dm = new DrawMountain();
        dm.setSize(1000, 700);
        dm.setDefaultCloseOperation(3);
        dm.setTitle(" 山脉生成 ");
        dm.setVisible(true);
    }

    public void paint(Graphics g) {
        // 重写 paint 方法中绘制操作
        super.paint(g);
        draw(g, 0, 200, this.getWidth(), 300, 200, 9);
    }

    /**
     * @param startX 当前线段的起点 X 坐标
     * @param startY 当前线段的起点 Y 坐标
     * @param endX   当前线段的终点 X 坐标
     * @param endY   当前线段的终点 Y 坐标
     * @param yRange Y 偏移的范围
     * @param times  递归次数
     */
    private void draw(Graphics g, double startX, double startY, double endX, double endY, double yRange, int times) {
        double centerX = (startX + endX) / 2;// 计算中点
        double centerY = (startY + endY) / 2;
        Random rand = new Random();
        double dyRand = rand.nextDouble() * 2 - 1;
        centerY = dyRand * yRange + centerY;
        if (--times == 0) {// 递归次数为 0 时，画线
            g.drawLine((int) startX, (int) startY, (int) centerX, (int) centerY);
            g.drawLine((int) endX, (int) endY, (int) centerX, (int) centerY);
        } else {
            yRange *= 0.8d;// 缩小 Y 方向偏移的范围
            draw(g, startX, startY, centerX, centerY, yRange, times);// 左半部分
            draw(g, centerX, centerY, endX, endY, yRange, times);// 右半部分
        }
    }
}