package chapter03.Capture07;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//运动显示测试
public class Capture extends JFrame {

    public static void main(String[] args) throws Exception {
        Capture cap = new Capture();
        cap.showVI();
    }

    public void showVI() throws Exception {
        this.setSize(1600, 800);
        this.setVisible(true);
        this.setTitle(" 视频运动追踪、去背景 ");
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage preImage = null; // 1. 保存上一张图片
        int imageCount = 0;// 隔几张图片计算一次
        while (true) {
            imageCount++;
            BufferedImage bi = webcam.getImage();// 2. 得到当前图片

            BufferedImage buﬀer = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);// 内存画图区
            Graphics graBuﬀer = buﬀer.getGraphics();
            for (int i = 0; i < bi.getWidth(); i += 1) {
                for (int j = 0; j < bi.getHeight(); j += 1) {
                    if (null != preImage) {
                        int diﬀ1 = Math.abs(bi.getRGB(i, j) - preImage.getRGB(i, j));
                        if ((diﬀ1) > 7888888) {// 3. 相同点差值过大，则认为动了
                            graBuﬀer.setColor(new Color(bi.getRGB(i, j)));
                            graBuﬀer.fillOval(i, j, 4, 4);
                        }
                    }
                }
            }
            this.getGraphics().drawImage(buﬀer, 10, 10, null);// 从缓冲区绘制到界面上
            if (imageCount % 5 == 0)// 4. 指向前一张图片，隔 5 张换一次
                preImage = bi;
        }
    }
}