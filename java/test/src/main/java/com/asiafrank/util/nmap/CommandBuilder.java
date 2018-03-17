package com.asiafrank.util.nmap;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Command -
 * do not support Mac OS, because it requires root privileges.
 * <p>
 * command usage: nmap [Scan Type(s)] [Options] {target specification}
 *
 * Build command by {@link Options}
 * Example:
 * <code>
 *     CommandBuilder cmd = CommandBuilder.newInstance("");
 *     cmd.addOptions(Options.O, Options.oX());
 *     cmd.addTargets("10.1.11.235", "10.1.240.79");
 *     System.out.println(Nmap.newInstance(cmd).call());
 * </code>
 * Not thread safe
 * </p>
 * Created at 12/8/2016.
 *
 * @author zhangxf
 */
public class CommandBuilder {
    private static final String app = "nmap";

    /**
     * Nmap HOME
     */
    private String directory;

    /**
     * options
     */
    private final LinkedList<String> options = new LinkedList<>();

    /**
     * targets
     */
    private final LinkedList<String> targets = new LinkedList<>();

    /**
     * Is command built?
     */
    private boolean isBuilt = false;

    /**
     * built command String
     */
    private String built;

    private CommandBuilder(String directory) {
        if (OSCheck.isMacOS()) {
            throw new IllegalArgumentException("System not supported");
        }

        if (directory == null) {
            throw new IllegalArgumentException("Directory should not be empty");
        }

        this.directory = directory;
    }

    /**
     * New instance
     * @param directory Nmap app home
     * @return CommandBuilder instance
     */
    public static CommandBuilder newInstance(String directory) {
        return new CommandBuilder(directory);
    }

    public static CommandBuilder newInstance() {
        return new CommandBuilder("");
    }

    public void addOption(String opt) {
        this.options.add(opt);
    }

    public void addOptions(String... opts) {
        Collections.addAll(this.options, opts);
    }

    public void addTarget(String ip) {
        this.targets.add(ip);
    }

    public void addTargets(String... ips) {
        Collections.addAll(this.targets, ips);
    }

    public String build() {
        if (isBuilt) {
            return built;
        }

        return rebuild();
    }

    public String rebuild() {
        isBuilt = false;

        if (!directory.equals("") && !directory.endsWith(File.separator)) {
            directory += File.separator;
        }

        String path = directory + app;

        LinkedList<String> list = new LinkedList<>();
        list.add(path);
        list.addAll(options);
        list.addAll(targets);

        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append(" ");
        }
        builder.append("--stats-every 1");

        built = builder.toString().trim();
        isBuilt = true;
        return built;
    }

    public String getDirectory() {
        return directory;
    }

    private static final class OSCheck {
        private static String OS = null;

        static String getOsName() {
            if (OS == null) {
                OS = System.getProperty("os.name").toLowerCase();
            }
            return OS;
        }

        static boolean isWindows() {
            return getOsName().startsWith("windows");
        }

        static boolean isUnix() {
            return getOsName().startsWith("unix");
        }

        static boolean isMacOS() {
            return getOsName().startsWith("mac");
        }
    }
}
