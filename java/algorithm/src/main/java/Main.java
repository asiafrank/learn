public class Main {

    public volatile int a = 1;
    public static void main(String[] args) {
//        System.out.println((int)'A');
//        System.out.println((int)'a');

        Main m = new Main();
        m.a = 2;
    }
}
