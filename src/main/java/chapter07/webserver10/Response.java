package chapter07.webserver10;

import java.io.OutputStream;

//miniWebServer 的 HTTP 应答对象的封装，输出数据给浏览器
public class Response {
    private final OutputStream ous;

    public Response(OutputStream ous) {


        this.ous = ous;
    }

    // 发送数据
    public void write(String msg) {
        try {
            ous.write(msg.getBytes());
            ous.flush();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }
}