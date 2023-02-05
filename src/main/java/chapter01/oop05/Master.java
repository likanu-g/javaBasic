package chapter01.oop05;

public class Master {

    public static void main(String[] args) {
        Animal a1 = new Bird();
        a1.eat(" 虫子 ");
        a1.move(3);

        Animal a2 = new Wolf();
        a2.eat(" 小羊 ");
        a2.move(10);
    }
}