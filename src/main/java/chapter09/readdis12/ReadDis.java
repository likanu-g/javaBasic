package chapter09.readdis12;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.lang.System.out;

//读取class文件，显示内容
public class ReadDis {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        out.println(" 请输入当前目录下要显示的文件名 : ");
        String fName = sc.next();
        FileInputStream fs = new FileInputStream(fName);
        byte[] data = new byte[fs.available()];
        fs.read(data);
        fs.close();
        out.println(fName + " 文件中的内容 :");

        showASCII(data);
        showHex(data);
    }

    // 显示为 ASCII 码格式
    private static void showASCII(byte[] data) throws Exception {
        out.println("\r\n\t 文件的 ASCII 内容是 ");
        String fc = new String(data, StandardCharsets.US_ASCII);
        for (int i = 0; i < fc.length(); i += 40) {
            int t = i + 40;
            if (t > fc.length())
                t = fc.length();
            String len = fc.substring(i, t);
            out.println(len);
        }
    }

    // 以十六进制字节显示
    private static void showHex(byte[] data) {
        out.println("\r\n\t 文件的字节内容是 ");
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() < 2)
                hex = "0" + hex;
            out.print(hex + "");
            if ((i + 1) % 16 == 0)
                out.println();
        }
    }
}