package coursex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 区间合并
 * LeetCode 56
 */
public class IntervalMerge {

    public static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }

    public static List<Interval> merge(List<Interval> list) {
        // 按 start 排序
        list.sort(Comparator.comparingInt(a -> a.start));

        List<Interval> rs = new ArrayList<>();
        Interval prev = list.get(0);

        int size = list.size();
        for (int i = 1; i < size; i++) {
            Interval curr = list.get(i);

            if (curr.start <= prev.end) {
                prev = new Interval(prev.start, Math.max(prev.end, curr.end));
            } else {
                rs.add(prev);
                prev = curr;
            }
        }
        rs.add(prev);
        return rs;
    }

    public static void main(String[] args) {
        List<Interval> list = Arrays.asList(
                new Interval(1, 2),
                new Interval(2, 3),
                new Interval(3, 4),
                new Interval(5, 6),
                new Interval(5, 5),
                new Interval(7, 8)
        );
        // 输出 [1,4] [5,6] [7,8]
        List<Interval> merge = merge(list);
        System.out.println(merge);
    }
}
