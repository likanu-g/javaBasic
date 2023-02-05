package chapter10.sample;
//代表要生成的咖啡

public class Coffe {
    public int id;
    String v;// 咖啡 id，品名

    public Coffe(int id, String v) {
        this.id = id;
        this.v = v;
    }

    public String toString() {
        return id + "-" + v;
    }
}
