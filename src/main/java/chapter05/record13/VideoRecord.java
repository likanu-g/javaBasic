package chapter05.record13;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//使用 webCamp 显示视频测试 http://webcam-capture.sarxos.pl
//录制视频：保存 100 张图片 播放视频：读取图片显示
public class VideoRecord extends JFrame {
    private Graphics g;

    public static void main(String[] args) {
        VideoRecord tf = new VideoRecord();
        tf.showFrame();
    }

    public void showFrame() {
        this.setTitle("webCam 保存图录像测试 ");
        this.setSize(500, 600);
        this.setLayout(new FlowLayout());
        JButton buPlay = new JButton("play");
        this.add(buPlay);
        this.setDefaultCloseOperation(3);

        this.setVisible(true);
        g = this.getGraphics();
        // 显示视频并保存 100 张图片
        this.showSaveVedio();
        // 读图片播放的按钮事件
        buPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });
    }

    // 1. 调用 webCam 库，获取摄像头
    public void showSaveVedio() {

        Webcam webcam = Webcam.getDefault();
        webcam.open();
        Graphics g = this.getGraphics();
        int count = 0;
        System.out.println(" 开始截取保存图片 ...");
        while (count < 100) {
            BufferedImage image = webcam.getImage();// 2. 从摄像头上获取一张图片，画出来
            g.drawImage(image, 50, 100, null);
            saveImage(count++, image); // 保存 100 张图片
            try {
                Thread.sleep(100);
            } catch (Exception ef) {
            }
        }
        javax.swing.JOptionPane.showMessageDialog(this, "100 张图片录制完毕 ");
    }

    // 图片压缩到文件（保存）
    public void saveImage(int id, BufferedImage image) {
        try {
            FileOutputStream fous = new FileOutputStream(id + "-video.jpg");
            ImageIO.write(image, "jpg", fous);
            fous.flush();
            fous.close();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 读取与加载连续的图片
    public void loadImage() {
        try {
            for (int id = 0; id < 100; id++) {
                FileInputStream ﬁns = new FileInputStream(id + "-video.jpg");
                BufferedImage image = ImageIO.read(ﬁns);
                g.drawImage(image, 100, 100, null);
                g.drawString(id + "-video.jpg", 80, 80);
                Thread.sleep(10);
            }
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        javax.swing.JOptionPane.showMessageDialog(this, "100 张图片播放完毕 ");
    }
}
