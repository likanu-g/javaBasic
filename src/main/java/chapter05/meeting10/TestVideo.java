package chapter05.meeting10;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;

public class TestVideo extends JFrame {

    public static void main(String[] args) {
        TestVideo tf = new TestVideo();
        tf.showFrame();
    }

    public void showFrame() {
        this.setTitle("webCam 视频测试 ");
        this.setSize(500, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.showVedio(); // 显示视频
    }

    public void showVedio() {
        // 1. 调用 webCam 库，取得摄像头
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        Graphics g = this.getGraphics();
        while (true) {
            // 2. 从摄像头上获取一张照片，画出来 BuﬀeredImage im=webcam.getImage();
            // g.drawImage(im, 0, 0, null);
        }
    }
}
