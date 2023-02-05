package chapter03.ImageBoard01;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageHead extends JFrame {
    private Graphics g;

    public static void main(String[] args) {
        ImageHead lu = new ImageHead();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle(" 美颜相机 ");
        this.setLayout(new FlowLayout());
        JButton buSrc = new JButton(" 原图 ");
        this.add(buSrc);
        this.setVisible(true);
        g = this.getGraphics();
        buSrc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadImage(g);
            }
        });
    }

    // 根据图片转换后的二维数组，绘制出原图
    public void loadImage(Graphics g) {
        int[][] data = image2Array("lenna.jpg");// 图片放在项目目录下
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                int v = data[i][j];
                Color c = new Color(v);
                g.setColor(c);
                g.drawLine(i + 50, j + 70, i + 50, j + 70);
            }
        }
    }

    // 将图片转成一个二维数组，一个点一个点在界面上画出
    private int[][] image2Array(String imageName) {
        File ﬁle = new File(imageName);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(ﬁle); // 从文件到图片对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = bi.getWidth(); // 图片的宽
        int h = bi.getHeight(); // 图片的高
        int[][] imIndex = new int[w][h];// 存储像素值的二维数组
        System.out.println("w=" + w + " h=" + h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int pixel = bi.getRGB(i, j); // i,j 位置的 Color 值
                imIndex[i][j] = pixel; // 每个像素点的 Color 存入数组
            }
        }
        return imIndex;
    }
}
