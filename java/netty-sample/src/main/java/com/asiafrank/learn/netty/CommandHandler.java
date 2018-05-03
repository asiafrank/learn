package com.asiafrank.learn.netty;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * @author zhangxf created at 5/2/2018.
 */
public class CommandHandler extends SimpleChannelInboundHandler<String> {

    private static final String PY_CMD     = "py ";
    private static final String GROOVY_CMD = "groovy ";
    private static final String CPP_CMD    = "cpp ";

    private static String root;

    private static PythonInterpreter  python;
    private static GroovyScriptEngine groovy;
    private static Binding binding = new Binding();

    public interface CLibrary extends StdCallLibrary, WinNT, Wincon {
        CLibrary INSTANCE = (CLibrary)Native.loadLibrary((Libs.isX64() ? "sample-x64" : "sample-x86"), CLibrary.class, W32APIOptions.DEFAULT_OPTIONS);

        CResult.ByValue scriptMain();
    }

    public CommandHandler() {
        URL url = Server.class.getClassLoader().getResource("application.properties");
        if (url == null)
            throw new RuntimeException("no url found for application.properties");
        File f = new File(url.getFile());
        root = f.getParent();
    }

    /**
     * check command and execute script, return the result
     * cmd list:
     * <ol>
     * <li>py sample (execute the sample.py)</li>
     * <li>groovy sample (execute the sample.groovy)</li>
     * <li>cpp sample (execute the sample.o or sample.dll)</li>
     * </ol>
     */
    private String cmd(String cmd) {
        if (cmd.startsWith(PY_CMD)) {
            return exec_py(cmd.substring(PY_CMD.length()).trim());
        } else if (cmd.startsWith(GROOVY_CMD)) {
            return exec_groovy(cmd.substring(GROOVY_CMD.length()).trim());
        } else if (cmd.startsWith(CPP_CMD)) {
            return exec_cpp(cmd.substring(CPP_CMD.length()).trim());
        }
        return "";
    }

    private String exec_py(String scriptName) {
        if (python == null) {
            Properties props = new Properties();
            props.put("python.home", "path to the Lib folder");
            props.put("python.console.encoding", "UTF-8");
            props.put("python.security.respectJavaAccessibility", "false");
            props.put("python.import.site", "false");
            Properties preprops = System.getProperties();
            PythonInterpreter.initialize(preprops, props, new String[0]);
            python = new PythonInterpreter();
        }

        python.execfile(root + "/" + scriptName + ".py");
        python.exec("result = scriptMain()");
        PyObject o = python.get("result");
        Result rs = (Result) o.__tojava__(Result.class);
        return rs.toString();
    }

    private String exec_groovy(String scriptName) {
        if (groovy == null) {
            try {
                groovy = new GroovyScriptEngine(new String[]{root});
            } catch (IOException e) {
                return "there is no engine for groovy script";
            }
        }

        String rs;
        try {
            Script s  = groovy.createScript(scriptName + ".groovy", binding);
            Result r = (Result) s.invokeMethod("scriptMain", null);
            rs = r.toString();
        } catch (ResourceException | ScriptException e) {
            e.printStackTrace();
            rs = e.getMessage();
        }
        return rs == null ? "" : rs;
    }

    private String exec_cpp(String scriptName) {
        CResult.ByValue rs = CLibrary.INSTANCE.scriptMain();
        return rs == null ? "" : rs.toString();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        // Generate and write a response.
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("quit".equals(request.toLowerCase())) {
            response = "Good bye!\r\n";
            close = true;
        } else {
            response = cmd(request);
        }

        // We do not need to write a ChannelBuffer here.
        // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
        ChannelFuture future = ctx.write(response);

        // Close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
