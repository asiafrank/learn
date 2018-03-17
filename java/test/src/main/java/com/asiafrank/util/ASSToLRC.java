package com.asiafrank.util;

import com.asiafrank.util.trash.EncodingDetect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class ASSToLRC {

    /*
     * 细节处理：
     * 	lastTempStr 记录上一行歌词通过都好分割的数组。
     * 	lastEndTime记录上一行歌词的结束时间
     * 	currentStartTime 记录当前行歌词的开始时间
     * 	判断：如果这两时间不相等，则空出一行，以lastEndTime作为这
     * 	空出行的开始时间。
     */
    public static String[] lastTempStr;
    public static String lastEndTime = "";
    public static String currentStartTime = "";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please input the ass file path:");
        String srcPath = in.nextLine().trim();
        System.out.println("Please input the generated file:");
        String destPath = in.nextLine().trim();

        System.out.println("\nYour ass file path is " + srcPath);
        System.out.println("\nYour generated file path is " + destPath);


        //String file = "C:\\Users\\Administrator\\Desktop\\y.ass";
        String encode = EncodingDetect.getJavaEncode(srcPath);
        System.out.println("\nFile encode : " + encode);

        //System.out.println("##Orignal");
        //"E:\\Downloads\\Music\\Karakuri Pierrot[en].ass"
        StringBuffer lrcVer = new StringBuffer();
        //lrcVer = convertToLRC(path);
        lrcVer = convert(srcPath, encode, destPath);
        System.out.println("\n");
        System.out.println("##After Convert");
        System.out.println(lrcVer);

        in.close();

    }

    /**
     * Convert to lyric text.
     * And output to a new text file
     *
     * @param file
     * @param code
     * @return
     */
    public static StringBuffer convert(String file, String code, String destPath) {
        StringBuffer sb = null;
        BufferedReader fr = null;
        FileWriter fw = null;
        try {
            sb = new StringBuffer();

            String myCode = code != null && !"".equals(code) ? code : "UTF8";
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), myCode);

            fr = new BufferedReader(read);
            fw = new FileWriter(destPath);

            /*
             * 记录“[Events]”出现次数。出现一次，就开始将每行的字符串
             * 进行处理成lrc格式后放入sb中。
             */
            boolean start = false;
            String line = null;
            // 读取每一行，如果结束了，line会为空
            while ((line = fr.readLine()) != null) {
                //System.out.println(line);
                if (!start && line.trim().startsWith("Dialogue")) {
                    start = true;
                }

                if (start) {
                    String strLine = StrConvert(line);
                    sb.append(strLine + "\n");
                    fw.write(strLine + "\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                    fr = null;
                }
                if (fw != null) {
                    fw.close();
                    fw = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return sb;
    }

    /**
     * 将那一行字符串进行处理，变成lrc格式。
     * 例： Dialogue: 0,0:00:00.00,0:00:05.00,Default,,0,0,0,,Karakuri Pierrot\n
     * 需要处理成 [00:00.00]Karakuri Pierrot
     */
    private static String StrConvert(String src) {

        StringBuffer dest = null;
        if (src != null && !src.trim().equals("")) {
            dest = new StringBuffer();
            String[] str = src.split(",");

            /*
             * 细节处理：
             * 	lastEndTime记录上一行歌词的结束时间
             * 	currentStartTime 记录当前行歌词的开始时间
             * 	判断：如果这两时间不相等，则空出一行，以lastEndTime作为这
             * 	空出行的开始时间。
             */
            currentStartTime = str[1].substring(2, 10);
            //System.out.println("lastEndTime == " + lastEndTime + "\ncurrentStartTime == " + currentStartTime);
            //在这里进行判断才有效
            if (lastEndTime != "" && !lastEndTime.trim().equals(currentStartTime)) {
                dest.append("[" + lastEndTime + "]\n");
            }

            lastEndTime = str[2].substring(2, 10);

            dest.append("[" + str[1].substring(2, 10) + "]");
            dest.append(str[str.length - 1]);
            //System.out.println("dest:" + dest);
        }

        if (dest == null) {
            return "";
        }
        return dest.toString();
    }

}
