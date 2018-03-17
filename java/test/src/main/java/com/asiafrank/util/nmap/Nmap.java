package com.asiafrank.util.nmap;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.*;

/**
 * Nmap
 * <p>
 * </P>
 * Created at 12/9/2016.
 *
 * @author zhangxf
 */
public class Nmap {

    private final CommandBuilder command;

    private final DefaultExecutor exec;

    private final PumpStreamHandler streamHandler;

    private final PipedInputStream in;

    private final PipedOutputStream out;

    private int exitValue;

    private String result;

    private Nmap(CommandBuilder command) throws IOException {
        this.command = command;
        this.exec = new DefaultExecutor();

        this.in = new PipedInputStream();
        this.out = new PipedOutputStream(in);
        this.streamHandler = new PumpStreamHandler(out);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
        exec.setWatchdog(watchdog);
    }

    public static Nmap newInstance(CommandBuilder command) throws IOException {
        return new Nmap(command);
    }

    public void call() {
        CommandLine commandline = CommandLine.parse(command.build());
        exec.setStreamHandler(streamHandler);
        try {
            exitValue = exec.execute(commandline);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean kill() {
        ExecuteWatchdog watchdog = exec.getWatchdog();
        watchdog.destroyProcess();
        return exec.isFailure(exitValue) && watchdog.killedProcess();
    }

    public String getResult() {
        return result;
    }

    public PipedInputStream getIn() {
        return in;
    }
}
