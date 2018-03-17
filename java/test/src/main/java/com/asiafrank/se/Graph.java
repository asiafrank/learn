package com.asiafrank.se;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Xiaofan Zhang on 2/2/2016.
 */
@Deprecated
public class Graph {
    public static void main(String[] args) {
        // 0: 白, 1: 红, 2: 蓝
        // x y轴要一致
        Integer[][] origin = {
                {0, 1, 1, 1},
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {2, 2, 2, 2}
        };

        Integer[][] goal = {
                {1, 1, 1, 1},
                {1, 2, 0, 1},
                {2, 2, 1, 2},
                {2, 2, 2, 2}
        };

        System.out.println(process(origin, goal));
    }

    private static String process(Integer[][] origin, Integer[][] goal) {
        List<Coordinate> steps = new ArrayList<Coordinate>();

        // 初始化棋子坐标 (0, 0) 目的地 (3, 1)
        Integer[][] temp = origin;
        Coordinate piece = new Coordinate(0 , 0);
        Coordinate dest = new Coordinate(3, 1);

        steps.add(new Coordinate(0, 0));
        int i = 0;
        while (true) {
            piece = forward(temp, goal, piece, dest, steps);
            Coordinate c = new Coordinate(piece.getX(), piece.getY());
            steps.add(c);
            /*if (piece.equals(dest)) {
                break;
            }*/
            i++;
            if (i == 5) {
                break;
            }
        }

        return steps.toString();
    }

    private static Coordinate forward(Integer[][] temp, Integer[][] goal, Coordinate piece, Coordinate dest, List<Coordinate> steps) {
        // step 1: (0, 1) or (1, 0)
        if (temp[0][0] == 0) {
            Integer t = temp[0][1];
            temp[0][1] = temp[0][0];
            temp[0][0] = t;
            piece.setX(0);
            piece.setY(1);
        } else {
            /**
             * prepare conditions
             */
            int x = piece.getX();
            int y = piece.getY();

            Integer c1 = goal[x][y]; // condition one

            List<Ele> c2 = new ArrayList<Ele>(); // condition two

            if (x >= 1) {
                Ele e = new Ele(new Coordinate(x - 1, y), temp[x - 1][y]);
                c2.add(e);
            }

            if (y >= 1) {
                Ele e = new Ele(new Coordinate(x, y - 1), temp[x][y - 1]);
                c2.add(e);
            }

            if (x <= 2) {
                c2.add(new Ele(new Coordinate(x + 1, y), temp[x + 1][y]));
            }

            if (y <= 2) {
                c2.add(new Ele(new Coordinate(x, y + 1), temp[x][y + 1]));
            }

            List<Ele> candidate = new ArrayList<Ele>();
            for (Ele e : c2) {
                if (e.getValue().equals(c1)) {
                    candidate.add(e);
                }
            }

            // 复制一个 candidate 以免原始数据污染
            List<Ele> candidateTemp = new ArrayList<Ele>();
            for (Ele e : candidate) {
                candidateTemp.add(e);
            }

            // 删除已走过的位置
            for (Ele e : candidateTemp) {
                if (steps.contains(e.getCoordinate())) {
                    candidate.remove(e);
                }
            }

            int ox = piece.getX();
            int oy = piece.getY();
            if (candidate.size() > 1) {
                // 比较 候选项 距离目的地最短的一个

                List<Coordinate> cos = new ArrayList<Coordinate>();
                // 取出 候选项中的 Coordinate
                for (Ele e : candidate) {
                    cos.add(e.getCoordinate());
                }

                Integer x0 = dest.getX();
                Integer y0 = dest.getY();
                List<Ele> lens = new ArrayList<Ele>();
                for (Coordinate c : cos) {
                    Integer tx = c.getX();
                    Integer ty = c.getY();
                    Ele e = new Ele(c, ((tx - x0) * (tx - x0)) + (ty - y0) * (ty - y0));
                    lens.add(e);
                }

                Collections.sort(lens);

                Coordinate co = lens.get(0).getCoordinate();
                Integer nx = co.getX();
                Integer ny = co.getY();

                Integer t = temp[nx][ny];
                temp[nx][ny] = temp[ox][oy];
                temp[ox][oy] = t;

                piece.setX(nx);
                piece.setY(ny);
            } else {
                Ele e = candidate.get(0);
                Coordinate next = e.getCoordinate();
                int nx = next.getX();
                int ny = next.getY();

                Integer t = temp[nx][ny];
                temp[nx][ny] = temp[ox][oy];
                temp[ox][oy] = t;

                piece.setX(nx);
                piece.setY(ny);
            }
        }

        return piece;
    }

    static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object obj) {
            Coordinate temp = (Coordinate)obj;
            if (temp.getX() == this.getX() && temp.getY() == this.getY()) {
                return true;
            } else {
                return false;
            }
        }
    }

    static class Ele implements Comparable<Ele> {
        private Coordinate coordinate;
        private Integer value;

        public Ele() {

        }
        public Ele(Coordinate coordinate, Integer value) {
            this.coordinate = coordinate;
            this.value = value;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        @Override
        public int compareTo(Ele o) {
            Integer v1 = this.getValue();
            Integer v2 = o.getValue();
            if (v1 < v2) {
                return -1;
            } else if (v1 > v2) {
                return 1;
            }
            return 0;
        }
    }
}
