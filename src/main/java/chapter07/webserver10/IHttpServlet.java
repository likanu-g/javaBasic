package chapter07.webserver10;

import java.util.Map;

//所有 Servlet 的父类
public abstract class IHttpServlet {
    // 属于一个用户的 session 对象
    protected Map<String, String> session;
    // 保存一次请求的键值对
    protected Map<String, String> parameters;

    // 在此方法中响应用户请求 Get 方法处理
    public abstract void service(Response res);
}