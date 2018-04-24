package com.asiafrank.doclet;

import com.asiafrank.doclet.utils.IOUtils;
import com.asiafrank.doclet.utils.TagPair;
import com.sun.javadoc.*;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

/**
 * <p>将 java 注释（html格式）过滤字符并输出成 markdown 文本。
 *
 * <pre>
 *  {@code}, {@docRoot}, {@index}, {@link}, {@linkplain}, {@literal}
 * </pre>
 *
 */
@SuppressWarnings("deprecation")
public class Markdown2Doclet extends Doclet {

    private static String newline = System.getProperty("line.separator");

    private static List<TagPair> pairs = new LinkedList<>();

    private static Node tagRoot;

    static {
        pairs.add(new TagPair("{@code"     , '}', "", ""));
        pairs.add(new TagPair("{@docRoot"  , '}', ".", ""));
        pairs.add(new TagPair("{@index"    , '}', "", ""));
        pairs.add(new TagPair("{@link"     , '}', "", ""));
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

        tagRoot = buildTree();

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

    private static Node buildTree() {
        if (pairs.isEmpty()) return new Node();

        Node root = new Node();
        root.root = true;
        root.value = '\0';
        root.nexts = new LinkedList<>();

        for (TagPair tp : pairs) {
            String tagStart = tp.tagStart;
            char[] chars = tagStart.toCharArray();
            int len = chars.length;
            for (int i = 0; i < len; i++) {
                insert(chars[i], root, tp, i == (len - 1));
            }
        }
        return root;
    }

    /**
     * 插入Node
     */
    private static void insert(char c, Node root, TagPair pair, boolean leaf) {
        List<Node> nodes = root.nexts;
        if (nodes.isEmpty()) {
            Node n = new Node();
            n.root = false;
            n.value = c;
            n.leaf = leaf;
        }
        // TODO:
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

    private static void transferAndWrite(String text, StringWriter writer) {
        char[] chars = text.toCharArray();

        Node curr = tagRoot;
        boolean writeToBuf = false;
        for (char c : chars) {
            Node child = isMatchChild(c, curr);
            if (child != null) {
                curr = child;
                if (child.leaf)
                    writeToBuf = true;
            }

            if (c == curr.end) {
                buf.flip(); // flip to read
                writer.append(curr.replaceStart);
                writer.append(buf.toString());
                writer.append(curr.replaceEnd);
                curr = tagRoot;
                buf.clear(); // clear for next write
                writeToBuf = false;
            }

            if (writeToBuf)
                buf.put(c);
            else
                writer.append(c);
        }
    }

    private static Node isMatchChild(char c, Node n) {
        if (n == null) return null;

        List<Node> nexts = n.nexts;
        if (nexts == null || nexts.isEmpty()) return null;

        for (Node n0 : nexts) {
            if (n0.value == c)
                return n0;
        }
        return null;
    }

    /**
     * 用它来构建语法树
     * replaceStart 和 replaceEnd 为 null 或者空串，代表完全过滤
     * Root 节点的 value 为 '\0'
     */
    private static class Node {
        /**
         * true 根节点即 tagRoot
         */
        boolean root;

        /**
         * 当前节点代表的字符
         */
        char value;

        /**
         * 下一个节点的列表，即孩子列表
         */
        List<Node> nexts;

        /**
         * 判断是否是叶子节点
         */
        boolean leaf;

        /**
         * 当 leaf = true 时，replaceStart 的值就是 buf 内容前的值
         */
        String replaceStart;

        /**
         * 当 leaf = true 时，replaceEnd 的值就是 buf 内容后的值
         */
        String replaceEnd;

        /**
         * 当 leaf = true 并且，字符串已经读到 end 了，buf 中的字符都需要输出到 Writer 中，并重置 clean buf
         */
        char end;
    }
}
