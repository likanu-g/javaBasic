package chapter07.webserver10;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

//1. 创建保持 session
//2. 匹配请求 Servlet
//3. 发送静态 HTML

public class WebServer {
    // 服务器端保存所有用户的 session
    private static final Map<String, HashMap<String, String>> sessions = new HashMap();

    // 为了频繁测试，每次生成不同的 session 后缀
    private static final String sessionEnd = "AAAAA";
    private static int count = 0;

    private static String creaateSessionID() {
        count++;
        String s = System.currentTimeMillis() + "";
        return s + count + sessionEnd;
    }

    // 带上了 sessionid 的 HTTP 头
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket so = ss.accept();
            new Thread(() -> {
                System.out.println(" 进入一个 HTTP client 请求：");
                try {
                    InputStream ins = so.getInputStream();
                    OutputStream ous = so.getOutputStream();
                    Response res = new Response(ous);
                    byte[] data = new byte[1024];
                    ins.read(data);
                    String inMsg = new String(data);
                    if (inMsg.indexOf("/favicon.ico") > 0) {
                        System.out.println(" 获取浏览器图标的请求，先忽略 ");
                        so.close();
                        return;
                    }
                    //1. 解析 HTTP 请求头中的 sessionid
                    String sessionID = parseSessionID(inMsg);
                    System.out.println("1. 解析到的 sessionID 是 " + sessionID);
                    // 即将查找的用户 session 对象
                    HashMap<String, String> userSession;
                    if (null == sessionID) {// 肯定是第一次访问
                        // 新建一个 sessionID
                        sessionID = creaateSessionID();
                        // 新建一个此用户的 session 对象，存入服务器全局 session 容器
                        userSession = new HashMap();
                        // userSession 是对某一个用户的，用 sessionID 关联；sessions 是服务器全局的
                        sessions.put(sessionID, userSession);
                        System.out.println("2. 新建用户 sessionID，存入全局 sessions " + sessionID);
                    } else {
                        userSession = sessions.get(sessionID);
                        System.out.println("2-1 来过，找到 session" + sessionID + " -" + userSession);
                    }
                    // 不管怎么样，先应答是正确的
                    String head = "HTTP/1.0 200 OK\r\n"
                            + "Set-Cookie: JSESSIONID=" + sessionID
                            + "\r\n" + "Server: batMimiServer/1.0\r\n"
                            + "Content-Type: text/html\r\n" + "\r\n";
                    ous.write(head.getBytes());
                    if (!inMsg.startsWith("GET")) {// 非 get 请求
                        res.write("this miniWebServer need GET request! ");
                        so.close();
                        return;
                    }


                    // 读求静态 HTML 文件，此处只发 login.html
                    if (inMsg.indexOf("login.html") > 0) {
                        sendHtml("login.html", ous);
                        ous.flush();
                        so.close();
                        return;
                    }
                    try {// 解析，匹配，映射到 Servlet toServlet(inMsg, userSession, res); ous.ﬂush();
                        so.close();
                        return;
                    } catch (Exception ef) {
                        ef.printStackTrace();
                        String errMsg = ef.getMessage();
                        String s = "<html><h1>toServlet 收到请求 <br>" + errMsg
                                + "<br> 系统时间： " + System.currentTimeMillis()
                                + "</h1></html>";
                        ous.write(s.getBytes());
                    } finally {
                        ins.close();
                        so.close();
                        System.out.println(" 一个请求结束 ！ ");
                    }
                } catch (Exception ef) {
                    ef.printStackTrace();
                }
            }).start();
        }
    }

    // 处理对 Servlet 的请求
    private static boolean toServlet(String inMsg, HashMap<String, String> userSession, Response res) throws Exception {
        int start = inMsg.indexOf("GET");
        int end = inMsg.indexOf("\r\n");
        String surl = inMsg.substring(start, end);
        // 载取 Servlet 请求路径，提取查询串
        String destUrl = parseServletPath(surl);
        System.out.println("3. 解析到的请求目标 ServletUrl:" + destUrl);
        // 载取 queryString 查询的键值对 name=hdf&&dest=bat
        Map<String, String> paras = new HashMap();
        try {
            // 解析 ? 后面的查询串，但可能没有查询串
            paras = parseQueryString(surl);
        } catch (Exception ef) {
            System.out.println("3.1 此请求没有 queryString");
        }
        // 根据 destUrl，判断要加载哪个 Servlet


        if (destUrl.equals("/login")) { // 请求 LoginServlet
            String servletClass = "miniWeb.servlet.LoginServlet";
            Class c = Class.forName(servletClass);
            Object o = c.newInstance();
            IHttpServlet servlet = (IHttpServlet) o;
            // 传入请求参数表
            servlet.session = userSession;
            servlet.parameters = paras;
            servlet.service(res);
            System.out.println("4. 目标 Servlet 执行完毕 :" + servletClass);
        } else if (destUrl.equals("register")) {// 请求 RegServlet String servletClass = "miniWeb.servlet.RegServlet";
            // 加载并调用，留给读者实现
        } else {// 没有匹配 URL
            // 为浏览器输出不匹配 URL 地址的错误提示
            // 留给读者实现
            throw new Exception(" 未找到对应 servlet<br> 你的请求地址是：<br>" + destUrl);
        }
        return false;
    }

    // 发送静态 HTML 文件请求：文件需存放在当前目录下
    public static void sendHtml(String ﬁleName, OutputStream ous) {
        try {
            // 默认都读取 login.html
            FileInputStream ﬁns = new FileInputStream(ﬁleName);
            int len = ﬁns.available();
            byte[] htmlData = new byte[len];
            ﬁns.read(htmlData);
            ous.write(htmlData);
            ﬁns.close();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 解析请求头中的 sessionID Cookie: JSESSIONID=16385081721531
    // 解析到则返回 ID，未解析到则返回 null
    private static String parseSessionID(String inHead) {
        int start = inHead.indexOf("JSESSIONID=") + "JSESSIONID=".length();
        int end = inHead.indexOf(sessionEnd);
        if (start > 0 && end > 0) {
            String sid = inHead.substring(start, end + sessionEnd.length());
            return sid;
        }
        return null;
    }

    // 请求路径中解析出 Servlet 路径


    private static String parseServletPath(String surl) {
        int start = surl.indexOf("/");
        int end = surl.indexOf("?");
        if (end <= 0) {
            end = surl.length();
        }
        String destUrl = surl.substring(start, end);
        return destUrl;
    }

    // 解析出请求中的键值对
    private static Map<String, String> parseQueryString(String surl) {
        // 保存查询串的键值对 name=hdf&&dest=bat
        Map<String, String> paras = new HashMap();
        // 截取 queryString 查询的键值对
        int start = surl.indexOf("?") + 1;// 去掉“？”
        int end = surl.indexOf("HTTP");
        String querStr = surl.substring(start, end).trim();
        StringTokenizer stk = new StringTokenizer(querStr, "&");
        while (stk.hasMoreTokens()) {
            String kvStr = stk.nextToken();
            String[] kv = kvStr.split("=");
            System.out.println(" 解析到的请求 Name:" + kv[0] + " 请求 value:" + kv[1]);
            paras.put(kv[0], kv[1]);// 将请求参数存入，供 Servlet 提取
        }
        return paras;
    }
}