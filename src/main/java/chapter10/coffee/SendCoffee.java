package chapter10.coffee;

import java.util.Random;
import java.util.concurrent.Callable;

public class SendCoffee implements Callable<String> {
    private final int CoffeeID;// 咖啡编号

    public SendCoffee(int CoffeeID) {
        this.CoffeeID = CoffeeID;
    }

    public String call() throws Exception {
        System.out.println(" 送咖啡 - 出门 ");
        Thread.sleep(1000);
        Random ran = new Random();
        int len = ran.nextInt(2000) + 2000;
        if (len > 3000) {
            return " 超时，客户取消订单 " + CoffeeID;
        } else {
            Thread.sleep(1000);
            System.out.println(" 客户说 OK");
            String rs = " 客户签字 OK" + CoffeeID;
            return rs;
        }
    }
}