package chapter03.VideoHaHa08;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class VideoHaHa extends JFrame {// 视频哈哈镜 - 放大镜示例

    public static void main(String[] args) {
        VideoHaHa tf = new VideoHaHa();
        tf.showFrame();
    }

    public void showFrame() {
        this.setTitle(" 视频创意 - 哈哈镜 ");
        this.setSize(500, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        while (true) {
            BufferedImage image = webcam.getImage();
            toHahaLeft(image);// 进行哈哈镜处理
            this.getGraphics().drawImage(image, 50, 50, 400, 400, null);
        }
    }

    // 代码有 bug，只对左侧进行哈哈镜
    public void toHahaLeft(BufferedImage bi) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        int cx = w / 2;
        int cy = h / 2;// 从图像中心处放大
        int r = 150; // 放大半径
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int tx = (i - cx);// 任意一点 tx,ty 到中心点 cx,cy 的距离
                int ty = (j - cy);
                int dis = (int) (Math.sqrt(tx * tx + ty * ty));
                if (dis < r) {// 当距离小于半径范围时，
                    // 得到新的偏移点位置，dis/r 是根据距离的偏移比例，远小近大
                    int nx = (tx) * dis / r + cx;
                    int ny = (ty) * dis / r + cy;
                    bi.setRGB(i, j, bi.getRGB(nx, ny));
                }
            }
        }
    }
}
