package cn.hd.interview.bitoptertion;

public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        t.取模();
    }

    private void 取模() {
        for (int i = 10; i < 20; i++) {
            int x = i % 8;
            int y = i & (8 - 1);
            System.out.println(x + " " + y);
        }
    }
}
