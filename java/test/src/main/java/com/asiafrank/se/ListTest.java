package com.asiafrank.se;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by asiafrank on 7/12/2015.
 */
public class ListTest {
    public static void main(String argv[]) {
        System.out.println("result: " + recursiveAdd(100));

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        for(int i=0;i<list.size();i++){
            list.remove(i);
        }

        Iterator<String> it = list.iterator();
        String a;
        for (;it.hasNext();) {
            a =  it.next();
            System.out.println(a);
        }
    }

    public static long recursiveAdd(long num) {
        if (num == 1) {
            return 1;
        }
        return num + recursiveAdd(--num);
    }

    public static String maskIDCardNo(String idCardNo){
        StringBuilder sb = new StringBuilder();
        sb.append(idCardNo.substring(0,6)).append("********").append(idCardNo.substring(14));
        return sb.toString();
    }

}
