package com.asiafrank.doclet;

import com.asiafrank.doclet.utils.IOUtils;
import com.asiafrank.doclet.utils.TagPair;
import com.sun.javadoc.*;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

/**
 * <p>将 java 注释（html格式）过滤字符并输出成 markdown 文本。
 * {@code 这是需要过滤的标签 code}
 * {@docRoot 这是需要过滤的标签 docRoot, 替换成了'.'}
 * {@literal 这是需要过滤的标签 literal}
 */
@SuppressWarnings("deprecation")
public class Markdown2Doclet extends Doclet {

    private static String newline = System.getProperty("line.separator");

    private static TagPair[] pairs = {
            new TagPair("{@code"     , '}', "", ""),
            new TagPair("{@docRoot"  , '}', ".", ""),
            new TagPair("{@index"    , '}', "", ""),
            new TagPair("{@link"     , '}', "", ""),
            new TagPair("{@linkplain", '}', "", ""),
            new TagPair("{@literal"  , '}', "", "")
    };

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

        // 多个标签写在一起，则无法正确过滤
        char    tagStartFlag = '{';
        boolean taging = false;
        int     tagingLength = getTagingLength();
        int     tagingIndex = 0;

        TagPair currTP = null;
        for (char c : chars) {
            if (currTP == null
                && c == tagStartFlag)
            {
                taging = true;
                tagbuf.put(c);
                continue;
            }

            if (taging) {
                tagbuf.put(c);
                tagingIndex++;
                if (tagingIndex == tagingLength) {
                    tagbuf.flip();
                    String tagStr = tagbuf.toString();
                    tagbuf.clear();
                    currTP = getTagPair(tagStr);
                    if (currTP == null) {
                        writer.append(tagStr);
                    } else {
                        writer.append(currTP.replaceStart);
                        writer.append(tagStr.substring(currTP.size));
                    }
                    taging = false;
                    tagingIndex = 0;
                    continue;
                }
                continue;
            }

            if (currTP == null) {
                writer.append(c);
            } else {
                if (c == currTP.tagEnd) {
                    writer.append(currTP.replaceEnd);
                    currTP = null;
                } else {
                    writer.append(c);
                }
            }
        }
        tagbuf.clear();
        buf.clear();
    }

    private static TagPair getTagPair(String tagStr) {
        TagPair lastTP = null;
        for (TagPair tp : pairs) {
            if (tagStr.startsWith(tp.tagStart)) {
                if (lastTP == null) {
                    lastTP = tp;
                } else {
                    if (tp.size > lastTP.size)
                        lastTP = tp;
                }
            }
        }
        return lastTP;
    }

    private static int getTagingLength() {
        int len = 0;
        for (TagPair tp : pairs) {
            if (len < tp.tagStart.length()) {
                len = tp.tagStart.length();
            }
        }
        return len;
    }
}
