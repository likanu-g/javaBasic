package chapter03.VideoHB06;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoHB extends JFrame {// 显示视频：处理成灰度图片

    public static void main(String[] args) {
        VideoHB tf = new VideoHB();
        tf.showFrame();
    }

    public void showFrame() {
        this.setTitle(" 美颜相机 - 视频创意 ");
        this.setSize(500, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        Graphics g = this.getGraphics();
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        while (true) { // 循环取得图片，画到界面上
            long start = System.currentTimeMillis();
            BufferedImage image = webcam.getImage();
            drawHBImage(g, image);
            long end = System.currentTimeMillis();// 测试每处理一张图片所用时间
            System.out.println("cost time : " + (end - start));
        }
    }

    // 将图片处理成灰度图
    public void drawHBImage(Graphics g, BufferedImage bi) {
        int w = bi.getWidth(); // 图片的宽
        int h = bi.getHeight(); // 图片的高
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int v = bi.getRGB(i, j);
                Color c = new Color(v);// 取得平均值，转成灰度图
                int avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                g.setColor(new Color(avg, avg, avg));
                g.drawLine(i + 50, j + 50, i + 50, j + 50);
            }
        }
    }
}