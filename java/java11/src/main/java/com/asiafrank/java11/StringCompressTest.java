package com.asiafrank.java11;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class StringCompressTest {
    public static void main(String[] args) {
        String src = "Learn <Introduction to Algorithms>";

        var buf = new byte[10 * 1024];
        String compressed = compress(src, buf);
        System.out.println("compressed: " + compressed);

        try {
            String uncompressed = decompress(compressed, buf);
            System.out.println("uncompressed: " + uncompressed);
        } catch (DataFormatException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩
     * @param inputString 源数据
     * @param buf 缓冲区，不能为 null
     * @return 经压缩后进行Base64加密成字符串返回
     */
    private static String compress(final String inputString, final byte[] buf) {
        // Encode a String into bytes
        byte[] input = inputString.getBytes(StandardCharsets.UTF_8);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Compress the bytes
        Deflater compressor = new Deflater();
        compressor.setInput(input, 0, input.length);
        compressor.finish();
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            if (count == 0) break;
            bos.write(buf, 0, count);
        }
        compressor.end();
        byte[] copy = bos.toByteArray();

        return Base64.getEncoder().encodeToString(copy);
    }

    /**
     * 解压
     * @param compressed Base64加密过的压缩数据
     * @param buf 缓冲区，不能为 null
     * @return 解压后的数据
     */
    private static String decompress(final String compressed, final byte[] buf) throws DataFormatException, UnsupportedEncodingException {
        byte[] src = Base64.getDecoder().decode(compressed);
        // Decompress the bytes

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(src, 0, src.length);
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                if (count == 0) break;
                bos.write(buf, 0, count);
            }
        } finally {
            decompressor.end();
        }

        // Decode the bytes into a String
        return bos.toString(StandardCharsets.UTF_8.name());
    }
}
