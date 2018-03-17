package com.asiafrank.se.blockmove;

/**
 * Created by Xiaofan Zhang on 3/2/2016.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate getInstance() {
        return new Coordinate();
    }

    public static Coordinate getInstance(int x, int y) {
        return new Coordinate(x, y);
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
}
