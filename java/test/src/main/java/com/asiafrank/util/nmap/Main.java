package com.asiafrank.util.nmap;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main - Test
 * <p>
 * </p>
 * Created at 1/18/2017.
 *
 * @author zhangxf
 */
public final class Main {
    public static void main(String[] args) throws IOException {
        ExecutorService exe = Executors.newSingleThreadExecutor();

        CommandBuilder cmd = CommandBuilder.newInstance("");
        cmd.addOptions(Options.O, Options.oX());
        cmd.addTargets("10.1.11.1/24");

        final Nmap nmap = Nmap.newInstance(cmd);
        InputStreamReader isr = new InputStreamReader(nmap.getIn());
        BufferedReader br = new BufferedReader(isr);

        StringBuilder rs = new StringBuilder();

        exe.submit(()-> {
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    rs.append(line).append("\n");
                    System.out.println("Line: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        nmap.call();

        System.out.println("========== result =========");
        System.out.println(rs);

        exe.shutdown();
        try {
            if (!exe.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("waiting to terminate");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
