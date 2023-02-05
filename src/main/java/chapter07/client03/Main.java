package chapter07.client03;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.Scanner;
//apache HttpClient下载官网 https://hc.apache.org/downloads.cgi

public class Main {
    // 使用 httpClient 组件，请求 URL 地址
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(" 请输入用户名 : ");
        String uName = sc.next();
        System.out.println(" 请输入请求密码 : ");
        String pwd = sc.next();

        String destAdd = "http://localhost:8080/userLogin?userName=" + uName + "&&pwd=" + pwd;
        System.out.println(" 完整的请求串是 : " + destAdd);
        System.out.println(" 请求将发送给服务器 ... ");
        // 创建 HTTP 对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(destAdd);
        // 实现一个 HTTP 返回结果处理器
        final HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
            @Override
            public String handleResponse(final ClassicHttpResponse response) throws IOException {
                final int status = response.getCode();
                // 如果结果成功

                if (status == HttpStatus.SC_OK) {
                    final HttpEntity entity = response.getEntity();
                    try {
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } catch (Exception ef) {
                        ef.printStackTrace();
                    }
                }

                System.out.println(" 请求失败 : 错误码 " + status);
                return " 错误 :" + status;
            }
        };
        String resp = httpclient.execute(httpget, responseHandler);
        System.out.println(" Spring RMI 服务器返回结果： " + resp);
    }
}
