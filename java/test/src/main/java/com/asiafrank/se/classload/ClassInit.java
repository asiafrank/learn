package com.asiafrank.se.classload;

public class ClassInit {

    private final int x = initX();

    {
        System.out.println("free block");
    }

    static {
        System.out.println("static block");
    }

    public ClassInit() {
        System.out.println("Constructor");
        new Inner() {
            @Override
            public void doing() {
                initX(); // unsafe
            }
        };
    }

    public static void main(String[] args) {
        ClassInit c = new ClassInit();
    }

    public int initX() {
        System.out.println("initX");
        return 42;
    }

    private interface Inner {
        void doing();
    }
}
