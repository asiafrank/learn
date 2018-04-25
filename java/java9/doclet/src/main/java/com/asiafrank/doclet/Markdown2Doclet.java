package com.asiafrank.doclet;

import com.asiafrank.doclet.utils.IOUtils;
import com.asiafrank.doclet.utils.TagPair;
import com.sun.javadoc.*;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

/**
 * <p>将 java 注释（html格式）过滤字符并输出成 markdown 文本。
 * {@code codexx}
 * <pre>
 *  {@code}, {@docRoot}, {@index}, {@link}, {@linkplain}, {@literal}
 * </pre>
 *
 */
@SuppressWarnings("deprecation")
public class Markdown2Doclet extends Doclet {

    private static String newline = System.getProperty("line.separator");

    private static List<TagPair> pairs = new LinkedList<>();

    static {
        pairs.add(new TagPair("{@code"     , '}', "", ""));
        pairs.add(new TagPair("{@docRoot"  , '}', ".", ""));
        pairs.add(new TagPair("{@index"    , '}', "", ""));
        pairs.add(new TagPair("{@link"     , '}', "", ""));
        pairs.add(new TagPair("{@linkplain", '}', "", ""));
        pairs.add(new TagPair("{@literal"  , '}', "", ""));
    }

    /**
     * {@inheritDoc}
     *
     * @param root RootDoc
     * @return true, success
     */
    public static boolean start(RootDoc root) {
        String[][] opts = root.options();

        String output = null;
        for (String[] opt : opts) {
            if (opt[0].equals("-output")) {
                output = opt[1];
            }
        }

        if (output == null || output.isEmpty()) return false;

        ClassDoc[] classes = root.classes();
        for (ClassDoc cls : classes) {
            File f = new File(output + File.separator + cls.name() + ".md");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                String content = loadClsContent(cls);
                fos.write(content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(fos);
            }
        }
        return true;
    }

    public static int optionLength(String option) {
        Map<String, Integer> options = new HashMap<>();
        options.put("-output", 2);

        Integer value = options.get(option);
        return Objects.requireNonNullElse(value, 0);
    }

    private static String loadClsContent(ClassDoc cls) {
        StringWriter writer = new StringWriter();

        String clsText = cls.commentText();
        transferAndWrite(clsText, writer);
        writer.append(newline);

        MethodDoc[] methods = cls.methods();
        for (MethodDoc m : methods) {
            String cmtText = m.commentText();
            if (cmtText != null && !cmtText.isEmpty()) {
                transferAndWrite(cmtText, writer);
                writer.append(newline);
            }

            ParamTag[] pts = m.paramTags();
            if (pts != null && pts.length > 0) {
                for (ParamTag t : pts) {
                    transferAndWrite(t.parameterComment(), writer);
                    writer.append(newline);
                }
            }
        }
        return writer.toString();
    }

    private static CharBuffer buf = CharBuffer.allocate(1024);
    private static CharBuffer tagbuf = CharBuffer.allocate(128);

    private static void transferAndWrite(String text, StringWriter writer) {
        char[] chars = text.toCharArray();

        // 目前只实现了过滤单个 TagPair
        // TODO: 同时过滤多个 TagPair
        // TODO: 遇到<pre></pre>标签，内容不过滤
        TagPair tp = pairs.get(0);
        char[] ta = tp.tagStartArray();
        int m = 0; // tag match index
        boolean tagToBuf = false;
        boolean contentToBuf = false;
        char tagEnd = '\0';

        for (char c : chars) {
            if (!tagToBuf) {
                if (c == ta[m]) {
                    tagToBuf = true;
                    m++;
                    tagbuf.put(c);
                    continue;
                }
            }

            if (tagToBuf) {
                tagbuf.put(c);
                if (c == ta[m]) { // match
                    if (m == ta.length - 1) {
                        contentToBuf = true;
                        tagEnd = tp.tagEnd;
                        m = 0;
                        writer.append(tp.replaceStart);
                        tagbuf.clear();
                        tagToBuf = false;
                        continue;
                    }
                    m++;
                    continue;
                } else { // not match
                    m = 0;
                    tagbuf.flip();
                    writer.append(tagbuf.toString());
                    tagbuf.clear();
                    tagToBuf = false;
                    continue;
                }
            }

            if (contentToBuf) {
                if (c == tagEnd) {
                    buf.flip();
                    writer.append(buf.toString());
                    writer.append(tp.replaceEnd);
                    buf.clear();
                    contentToBuf = false;
                    continue;
                }
                buf.put(c);
                continue;
            }

            writer.append(c);
        }
    }
}
