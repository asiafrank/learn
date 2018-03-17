package com.asiafrank.util.nmap;

/**
 * Options
 * <p>
 * command usage: nmap [Scan Type(s)] [Options] {target specification}
 * command example:
 * <code>
 *     nmap -v -A scanme.nmap.org
 *     nmap -v -sn 192.168.0.0/16 10.0.0.0/8
 *     nmap -v -iR 10000 -Pn -p 80
 * </code>
 *
 * Reference: https://nmap.org/book/man-briefoptions.html
 * SEE THE MAN PAGE (https://nmap.org/book/man.html) FOR MORE OPTIONS AND EXAMPLES
 * </p>
 * Created at 12/8/2016.
 *
 * TODO: verify method params
 * @author zhangxf
 */
public final class Options {
    private static final String noParam = "-";
    private static final String comma = ",";
    private static final String space = " ";

    //==================================================
    // TARGET SPECIFICATION
    //==================================================

    /**
     * -iL <inputfilename>: Input from list of hosts/networks
     *
     * Example: Scan targets from a text file
     * <code>
     *      nmap -iL list-of-ips.txt
     * </code>
     */
    public static String iL(String inputfilename) {
        return oneParam("-iL", inputfilename);
    }

    /**
     * -iR <num hosts>: Choose random targets
     *
     * Example:
     * <code>
     *      nmap -v -iR 10000 -Pn -p 80
     * </code>
     */
    public static String iR(int num) {
        return oneParam("-iR", num);
    }

    /**
     * --exclude <host1[,host2][,host3],...>: Exclude hosts/networks
     */
    public static String exclude(String... hosts) {
        return multiParams("--exclude", hosts);
    }

    /**
     * --excludefile <exclude_file>: Exclude list from file
     */
    public static String excludefile(String excludeFile) {
        return oneParam("--excludefile", excludeFile);
    }

    //==================================================
    // HOST DISCOVERY
    //==================================================

    /**
     * -sL: List Scan - simply list targets to scan
     */
    public static final String sL = "-sL";

    /**
     * -sn: Ping Scan - disable port scan
     */
    public static final String sn = "-sn";

    /**
     * -Pn: Treat all hosts as online -- skip host discovery
     */
    public static final String Pn = "-Pn";

    /**
     * -PS[portlist]: TCP SYN discovery to given ports
     */
    public static String PS(int... portlist) {
        return multiParamsNoSpace("-PS", portlist);
    }

    /**
     * -PA[portlist]: TCP ACK discovery to given ports
     */
    public static String PA(int... portlist) {
        return multiParamsNoSpace("-PA", portlist);
    }

    /**
     * -PU[portlist]: UDP discovery to given ports
     */
    public static String PU(int... portlist) {
        return multiParamsNoSpace("-PU", portlist);
    }

    /**
     * -PY[portlist]: SCTP discovery to given ports
     */
    public static String PY(int... portlist) {
        return multiParamsNoSpace("-PY", portlist);
    }

    /**
     * -PE: ICMP echo request discovery probes
     */
    public static final String PE = "-PE";

    /**
     * -PP: Timestamp request discovery probes
     */
    public static final String PP = "-PP";

    /**
     * -PM: Netmask request discovery probes
     */
    public static final String PM = "-PM";

    /**
     * -PO[protocol list]: IP Protocol Ping
     *
     * One of the newer host discovery options is the IP protocol ping,
     * which sends IP packets with the specified protocol number set in their IP header.
     * The protocol list takes the same format as do port lists in the previously discussed
     * TCP, UDP and SCTP host discovery options. If no protocols are specified,
     * the default is to send multiple IP packets for ICMP (protocol 1), IGMP (protocol 2),
     * and IP-in-IP (protocol 4). The default protocols can be configured at compile-time
     * by changing DEFAULT_PROTO_PROBE_PORT_SPEC in nmap.h. Note that for the ICMP, IGMP,
     * TCP (protocol 6), UDP (protocol 17) and SCTP (protocol 132), the packets are sent
     * with the proper protocol headers while other protocols are sent with no additional
     * data beyond the IP header (unless any of --data, --data-string, or --data-length
     * options are specified).
     *
     * This host discovery method looks for either responses using the same protocol as a probe, or ICMP protocol unreachable messages which signify that the given protocol isn't supported on the destination host. Either type of response signifies that the target host is alive.
     *
     * Ref: https://nmap.org/book/man-host-discovery.html
     */
    public static String PO(int... protocols) {
        return multiParamsNoSpace("-PO", protocols);
    }

    /**
     * -n: Never do DNS resolution
     * Alternate {@link #R}
     * [default: sometimes]
     */
    public static final String n = "-n";

    /**
     * -R: Always do DNS resolve
     * Alternate {@link #n}
     * [default: sometimes]
     */
    public static final String R = "-R";

    /**
     * --dns-servers <serv1[,serv2],...>: Specify custom DNS servers
     */
    public static String dns_servers(String... servers) {
        return multiParams("--dns-servers", servers);
    }

    /**
     * --system-dns: Use OS's DNS resolver
     */
    public static final String system_dns = "--system-dns";

    /**
     * --traceroute: Trace hop path to each host
     */
    public static final String traceroute = "--traceroute";

    //==================================================
    // SCAN TECHNIQUES - Ref: https://nmap.org/book/man-port-scanning-techniques.html
    //==================================================

    /**
     * -sS: TCP SYN scans
     */
    public static final String sS = "-sS";

    /**
     * -sT: Connect() scans
     */
    public static final String sT = "-sT";

    /**
     * -sA: ACK scans
     */
    public static final String sA = "-sA";

    /**
     * -sW: Window scans
     */
    public static final String sW = "-sW";

    /**
     * -sM: Maimon scans
     */
    public static final String sM = "-sM";

    /**
     * -sU: UDP Scan
     */
    public static final String sU = "-sU";

    /**
     * -sN: TCP Null scans
     */
    public static final String sN = "-sN";

    /**
     * -sF/: FIN scans
     */
    public static final String sF = "-sF";

    /**
     * -sX: Xmas scans
     */
    public static final String sX = "-sX";

    /**
     * --scanflags <flags>: Customize TCP scan flags
     *
     * Truly advanced Nmap users need not limit themselves to the canned scan types offered.
     * The --scanflags option allows you to design your own scan by specifying arbitrary TCP flags.
     * Let your creative juices flow, while evading intrusion detection systems whose vendors simply paged
     * through the Nmap man page adding specific rules!
     *
     * The --scanflags argument can be a numerical flag value such as 9 (PSH and FIN),
     * but using symbolic names is easier. Just mash together any combination of URG, ACK, PSH, RST, SYN, and FIN.
     * For example, --scanflags URGACKPSHRSTSYNFIN sets everything, though it's not very useful for scanning.
     * The order these are specified in is irrelevant.
     *
     * In addition to specifying the desired flags, you can specify a TCP scan type (such as -sA or -sF).
     * That base type tells Nmap how to interpret responses. For example, a SYN scan considers no-response to
     * indicate a filtered port, while a FIN scan treats the same as open|filtered. Nmap will behave the same way
     * it does for the base scan type, except that it will use the TCP flags you specify instead.
     * If you don't specify a base type, SYN scan is used.
     *
     * Ref: https://nmap.org/book/man-port-scanning-techniques.html
     */
    public static String scanflags(String flags) {
        return oneParam("--scanflags", flags);
    }

    /**
     * -sI <zombie host[:probeport]>: Idle scan
     */
    public static String sI(String zombieHost) {
        return oneParam("-sI", zombieHost);
    }

    /**
     * -sY: SCTP INIT scans
     */
    public static final String sY = "-sY";

    /**
     * -sZ: COOKIE-ECHO scans
     */
    public static final String sZ = "-sZ";

    /**
     * -sO: IP protocol scan
     */
    public static final String sO = "-sO";

    /**
     * -b <FTP relay host>: FTP bounce scan
     *
     */
    public static String b(String host) {
        return oneParam("-b", host);
    }

    //==================================================
    // PORT SPECIFICATION AND SCAN ORDER - Ref: https://nmap.org/book/man-port-specification.html
    //==================================================

    /**
     * -p <port ranges>: Only scan specified ports
     * Example: -p22; -p1-65535; -p U:53,111,137,T:21-25,80,139,8080,S:9
     */
    public static String p(String... ranges) {
        return portRangesParams("-p", ranges);
    }

    /**
     * --exclude-ports <port ranges>: Exclude the specified ports from scanning
     */
    public static String exclude_ports(String ranges) {
        return multiParams("--exclude-ports", ranges);
    }

    /**
     * -F: Fast mode - Scan fewer ports than the default scan
     */
    public static final String F = "-F";

    /**
     * -r: Scan ports consecutively - don't randomize
     */
    public static final String r = "-r";

    /**
     * --top-ports <number>: Scan <number> most common ports
     */
    public static String top_ports(int number) {
        return oneParam("--top-ports", number);
    }

    /**
     * --port-ratio <ratio>: Scan ports more common than <ratio> <decimal number between 0 and 1>
     */
    public static String port_ratio(double ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("ratio should between 0 and 1");
        }
        return oneParam("--port-ratio", ratio + "");
    }

    //==================================================
    // SERVICE/VERSION DETECTION - Ref: https://nmap.org/book/man-version-detection.html
    //==================================================

    /**
     * -sV: Probe open ports to determine service/version info
     */
    public static final String sV = "-sV";

    /**
     * --version-intensity <level>: Set from 0 (light) to 9 (try all probes)
     */
    public static String version_intensity(int level) {
        return oneParam("--version-intensity", level);
    }

    /**
     * --version-light: Limit to most likely probes (intensity 2)
     */
    public static final String version_light = "--version-light";

    /**
     * --version-all: Try every single probe (intensity 9)
     */
    public static final String version_all = "--version-all";

    /**
     * --version-trace: Show detailed version scan activity (for debugging)
     */
    public static final String version_trace = "--version-trace";

    //==================================================
    // SCRIPT SCAN - Ref: https://nmap.org/book/man-nse.html
    //==================================================

    /**
     * -sC: equivalent to --script=default
     */
    public static final String sC = "-sC";

    /**
     * --script <Lua scripts>: <Lua scripts> is a comma separated list of
     *                         directories, script-files or script-categories
     */
    public static String script(String... scripts) {
        return multiParams("--script", scripts);
    }

    /**
     * --script-args <n1=v1,[n2=v2,...]>: provide arguments to scripts
     * Example: --script-args 'user=foo,pass=",{}=bar",whois={whodb=nofollow+ripe},xmpp-info.server_name=localhost'
     */
    public static String script_args(String... args) {
        return multiParams("--script-args", args);
    }

    /**
     * --script-args-file <filename>: provide NSE script args in a file
     */
    public static String script_args_file(String filename) {
        return oneParam("--script-args-file", filename);
    }

    /**
     * --script-trace: Show all data sent and received
     */
    public static final String script_trace = "--script-trace";

    /**
     * --script-updatedb: Update the script database.
     */
    public static final String script_updatedb = "--script-updatedb";

    /**
     * --script-help <Lua scripts>: Show help about scripts.
     *                              <Lua scripts> is a comma-separated list of script-files or
     *                              script-categories.
     */
    public static String script_help(String... scripts) {
        return multiParams("--script-help", scripts);
    }

    //==================================================
    // OS DETECTION - Ref: https://nmap.org/book/man-os-detection.html
    //==================================================

    /**
     * -O: Enable OS detection
     */
    public static final String O = "-O";

    /**
     * --osscan-limit: Limit OS detection to promising targets
     */
    public static final String osscan_limit = "--osscan-limit";

    /**
     * --osscan-guess: Guess OS more aggressively
     */
    public static final String osscan_guess = "--osscan-guess";

    //==================================================
    // TIMING AND PERFORMANCE
    // Options which take <time> are in seconds, or append 'ms' (milliseconds),
    // 's' (seconds), 'm' (minutes), or 'h' (hours) to the value (e.g. 30m).
    //==================================================

    /**
     * -T<0-5>: Set timing template (higher is faster)
     * paranoid (0), sneaky (1), polite (2), normal (3), aggressive (4), and insane (5)
     */
    public static String T(int timing) {
        return "-T" + timing;
    }

    /**
     * --min-hostgroup <size>: Parallel host scan group sizes
     */
    public static String min_hostgroup(int size) {
        return oneParam("--min-hostgroup", size);
    }

    /**
     * --max-hostgroup <size>: Parallel host scan group sizes
     */
    public static String max_hostgroup(int size) {
        return oneParam("--max-hostgroup", size);
    }

    /**
     * --min-parallelism <numprobes>: Probe parallelization
     */
    public static String min_parallelism(int numprobes) {
        return oneParam("--min-parallelism", numprobes);
    }

    /**
     * --max-parallelism <numprobes>: Probe parallelization
     */
    public static String max_parallelism(int numprobes) {
        return oneParam("--max-parallelism", numprobes);
    }

    /**
     * --min-rtt-timeout <time>: Specifies probe round trip time.
     */
    public static String min_rtt_timeout(int time) {
        return oneParam("--min-rtt-timeout", time);
    }

    /**
     * --max-rtt-timeout <time>: Specifies probe round trip time.
     */
    public static String max_rtt_timeout(int time) {
        return oneParam("--max-rtt-timeout", time);
    }

    /**
     * --initial-rtt-timeout <time>: Specifies probe round trip time.
     */
    public static String initial_rtt_timeout(int time) {
        return oneParam("--initial-rtt-timeout", time);
    }

    /**
     * --max-retries <tries>: Caps number of port scan probe retransmissions.
     */
    public static String max_retries(int tries) {
        return oneParam("--max-retries", tries);
    }

    /**
     * --host-timeout <time>: Give up on target after this long
     */
    public static String host_timeout(int time) {
        return oneParam("--host-timeout", time);
    }

    /**
     * --scan-delay <time>: Adjust delay between probes
     */
    public static String scan_delay(int time) {
        return oneParam("--scan-delay", time);
    }

    /**
     * --max-scan-delay <time>: Adjust delay between probes
     */
    public static String max_scan_delay(int time) {
        return oneParam("--max-scan-delay", time);
    }

    /**
     * --min-rate <number>: Send packets no slower than <number> per second
     */
    public static String min_rate(int number) {
        return oneParam("--min-rate", number);
    }

    /**
     * --max-rate <number>: Send packets no faster than <number> per second
     */
    public static String max_rate(int number) {
        return oneParam("--max-rate", number);
    }

    //==================================================
    // FIREWALL/IDS EVASION AND SPOOFING - Ref: https://nmap.org/book/man-bypass-firewalls-ids.html
    //==================================================

    /**
     * -f; --mtu <val>: fragment packets (optionally w/given MTU)
     */
    public static final String f = "-f";

    /**
     * -f; --mtu <val>: fragment packets (optionally w/given MTU)
     */
    public static String mtu(int val) {
        return oneParam("--mtu", val);
    }

    /**
     * -D <decoy1,decoy2[,ME],...>: Cloak a scan with decoys
     */
    public static String D(String... decoys) {
        return multiParams("-D", decoys);
    }

    /**
     * -S <IP_Address>: Spoof source address
     */
    public static String S(String ip) {
        return oneParam("-S", ip);
    }

    /**
     * -e <iface>: Use specified interface
     */
    public static String e(String iface) {
        return oneParam("-e", iface);
    }

    /**
     * -g <portnum>: Use given port number
     */
    public static String g(int portnum) {
        return oneParam("-g", portnum );
    }

    /**
     * --source-port <portnum>: Use given port number
     */
    public static String source_port(int portnum) {
        return oneParam("--source-port", portnum);
    }

    /**
     * --proxies <url1,[url2],...>: Relay connections through HTTP/SOCKS4 proxies
     */
    public static String proxies(String... urls) {
        return multiParams("--proxies", urls);
    }

    /**
     * --data <hex string>: Append a custom payload to sent packets
     */
    public static String data(String hex) {
        return oneParam("--data", hex);
    }

    /**
     * --data-string <string>: Append a custom ASCII string to sent packets
     */
    public static String data_string(String str) {
        return oneParam("--data-string", str);
    }

    /**
     * --data-length <num>: Append random data to sent packets
     */
    public static String data_length(int num) {
        return oneParam("--data-length", num);
    }

    /**
     * --ip-options <options>: Send packets with specified ip options
     */
    public static String ip_options(String opts) {
        return oneParam("--ip-options", opts);
    }

    /**
     * --ttl <val>: Set IP time-to-live field
     */
    public static String ttl(int val) {
        return oneParam("--ttl", val);
    }

    /**
     * --spoof-mac <mac address/prefix/vendor name>: Spoof your MAC address
     */
    public static String spoof_mac(String name) {
        return oneParam("--spoof-mac", name);
    }

    /**
     * --badsum: Send packets with a bogus TCP/UDP/SCTP checksum
     */
    public static final String badsum = "--badsum";

    //==================================================
    // OUTPUT - Ref: https://nmap.org/book/man-output.html
    //==================================================

    /**
     * -oN <file>: Output scan in normal format, respectively, to the given filename.
     */
    public static String oN(String file) {
        return fileParam("-oN", file);
    }

    public static String oN() {
        return oN("");
    }

    /**
     * -oX <file>: Output scan in normal, XML, s|<rIpt kIddi3,
     * and Grepable format, respectively, to the given filename.
     */
    public static String oX(String file) {
        return fileParam("-oX", file);
    }

    public static String oX() {
        return oX("");
    }

    /**
     * -oN/-oX/-oS/-oG <file>: Output scan in normal, XML, s|<rIpt kIddi3,
     * and Grepable format, respectively, to the given filename.
     */
    public static String oS(String file) {
        return fileParam("-oS", file);
    }

    public static String oS() {
        return oS("");
    }

    /**
     * -oG <file>: Output scan in normal, XML, s|<rIpt kIddi3,
     * and Grepable format, respectively, to the given filename.
     */
    public static String oG(String file) {
        return fileParam("-oG", file);
    }

    public static String oG() {
        return oG("");
    }

    /**
     * -oA <basename>: Output in the three major formats at once
     *
     * @param basename file basename, it will generate basename.xml, basename.nmap, basename.gmap files.
     */
    public static String oA(String basename) {
        return oneParam("-oA", basename);
    }

    /**
     * -v: Increase verbosity level (use -vv or more for greater effect)
     */
    public static final String v = "-v";

    /**
     * Greater effect than {@link #v}
     */
    public static final String vv = "-vv";

    /**
     * -d: Increase debugging level (use -dd or more for greater effect)
     */
    public static final String d = "-d";

    /**
     * Greater effect than {@link #d}
     */
    public static final String dd = "-dd";

    /**
     * --reason: Display the reason a port is in a particular state
     */
    public static final String reason = "--reason";

    /**
     * --open: Only show open (or possibly open) ports
     */
    public static final String open = "--open";

    /**
     * --packet-trace: Show all packets sent and received
     */
    public static final String packet_trace = "--packet-trace";

    /**
     * --iflist: Print host interfaces and routes (for debugging)
     */
    public static final String iflist = "--iflist";

    /**
     * --append-output: Append to rather than clobber specified output files
     */
    public static final String append_output = "--append-output";

    /**
     * --resume <filename>: Resume an aborted scan
     */
    public static String resume(String filename) {
        return oneParam("--resume", filename);
    }

    /**
     * --stylesheet <path/URL>: XSL stylesheet to transform XML output to HTML
     */
    public static String stylesheet(String url) {
        return oneParam("--stylesheet", url);
    }

    /**
     * --webxml: Reference stylesheet from Nmap.Org for more portable XML
     */
    public static final String webxml = "--webxml";

    /**
     * --no-stylesheet: Prevent associating of XSL stylesheet w/XML output
     */
    public static final String no_stylesheet = "--no-stylesheet";

    //==================================================
    // MISC
    //==================================================

    /**
     * -6: Enable IPv6 scanning
     */
    public static final String IPv6 = "-6";

    /**
     * -A: Enable OS detection, version detection, script scanning, and traceroute
     */
    public static final String A = "-A";

    /**
     * --datadir <dirname>: Specify custom Nmap data file location
     */
    public static String datadir(String dirname) {
        return oneParam("--datadir", dirname);
    }

    /**
     * --send-eth/--send-ip: Send using raw ethernet frames or IP packets
     * {@link #send_ip}
     */
    public static final String send_eth = "--send-eth";

    /**
     * --send-eth/--send-ip: Send using raw ethernet frames or IP packets
     * {@link #send_eth}
     */
    public static final String send_ip = "--send-ip";

    /**
     * --privileged: Assume that the user is fully privileged
     */
    public static final String privileged = "--privileged";

    /**
     * --unprivileged: Assume the user lacks raw socket privileges
     */
    public static final String unprivileged = "--unprivileged";

    /**
     * -V: Print version number
     */
    public static final String V = "-V";

    /**
     * -h: Print this help summary page.
     */
    public static final String h = "-h";

    //==================================================
    // Other method
    //==================================================

    /**
     * Build with one param
     */
    private static String oneParam(String opt, String param) {
        if (param == null || param.isEmpty()) {
            throw new IllegalArgumentException("Param should not be empty");
        }
        return opt + space + param;
    }

    /**
     * Build with one int param
     */
    private static String oneParam(String opt, int param) {
        return opt + space + param;
    }

    /**
     *  Only for -oN/-oX/-oS/-oG
     */
    private static String fileParam(String opt, String filename) {
        if (opt == null
                || (!opt.equals("-oN")
                && !opt.equals("-oX")
                && !opt.equals("-oS")
                && !opt.equals("-oG"))) {
            throw new IllegalArgumentException("Option should be -oN/-oX/-oS/-oG");
        }

        if (filename == null || filename.isEmpty()) {
            filename = noParam;
        }
        return opt + space + filename;
    }

    /**
     *  Build with multi params
     */
    private static String multiParams(boolean needSpace, String opt, String... params) {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("Params should not be empty");
        }

        StringBuilder sb = new StringBuilder(opt + (needSpace? space : ""));
        for (String s : params) {
            sb.append(s).append(comma);
        }

        // remove last comma
        String rs = sb.toString();
        if (rs.endsWith(comma)) {
            rs = rs.substring(0, rs.length() - 1);
        }
        return rs;
    }

    /**
     * Build with multi int params
     */
    private static String multiParams(String opt, int... params) {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("Params should not be empty");
        }

        String[] newParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            newParams[i] = params[i] + "";
        }
        return multiParams(true, opt, newParams);
    }

    /**
     *  Build with multi params - needSpace == true
     */
    private static String multiParams(String opt, String... params) {
        return multiParams(true, opt, params);
    }

    /**
     *  Build with multi params - needSpace == false
     */
    private static String multiParamsNoSpace(String opt, String... params) {
        return multiParams(false, opt, params);
    }

    /**
     *  Build with multi int params - needSpace == false
     */
    private static String multiParamsNoSpace(String opt, int... params) {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("Params should not be empty");
        }

        String[] newParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            newParams[i] = params[i] + "";
        }
        return multiParams(false, opt, newParams);
    }

    /**
     * Multi param for port ranges
     */
    private static String portRangesParams(String opt, String... ranges) {
        if (ranges == null || ranges.length == 0) {
            throw new IllegalArgumentException("Ranges should not be empty");
        }

        String startElement = ranges[0];
        if (Character.isDigit(startElement.charAt(0))) {
            return multiParamsNoSpace(opt, ranges);
        } else {
            return multiParams(opt, ranges);
        }
    }
}
