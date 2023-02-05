package chapter03.VideoShow05;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//Webcam下载官网 https://github.com/sarxos/webcam-capture

public class VideoShow extends JFrame {// 显示视频

    public static void main(String[] args) {
        VideoShow tf = new VideoShow();
        tf.showFrame();
    }

    public void showFrame() {
        this.setTitle(" 美颜相机 - 视频创意 ");
        this.setSize(500, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        Graphics g = this.getGraphics();
        Webcam webcam = Webcam.getDefault();// 选取一个摄像头取得视频
        webcam.open();
        while (true) { // 循环取得图片，画到界面上
            BufferedImage image = webcam.getImage();
            g.drawImage(image, 50, 50, null);
        }
    }
}