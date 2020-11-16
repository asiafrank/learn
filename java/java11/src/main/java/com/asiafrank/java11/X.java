package com.asiafrank.java11;

public class X {
    public static final String before = "tps: 449.53 qps: 449.53\n" +
            "tps: 1185.85 qps: 1185.85\n" +
            "tps: 1601.26 qps: 1601.26\n" +
            "tps: 2207.11 qps: 2207.11\n" +
            "tps: 2024.92 qps: 2024.92\n" +
            "tps: 2488.32 qps: 2488.32\n" +
            "tps: 1849.22 qps: 1849.22\n" +
            "tps: 1098.55 qps: 1098.55\n" +
            "tps: 2445.71 qps: 2445.71\n";
    public static final String after =
            "tps: 1674.74 qps: 1674.74\n" +
            "tps: 2147.74 qps: 2147.74\n" +
            "tps: 2581.03 qps: 2581.03\n" +
            "tps: 2825.16 qps: 2825.16\n" +
            "tps: 2791.81 qps: 2791.81\n" +
            "tps: 2966.96 qps: 2966.96\n" +
            "tps: 3037.32 qps: 3037.32\n" +
            "tps: 3049.49 qps: 3049.49\n" +
            "tps: 2897.42 qps: 2897.42";

    public static void main(String[] args) {
        String[] befores = before.split("\n");
        String[] afters = after.split("\n");
        int len = befores.length;
        for (int i = 0; i < len; i++) {
            String tps = befores[i];
        }

        System.out.println(System.getProperty("java.system.class.loader"));

        final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };
        System.out.println(CONTENT.length);
    }
}
