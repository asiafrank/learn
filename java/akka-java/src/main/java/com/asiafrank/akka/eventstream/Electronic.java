package com.asiafrank.akka.eventstream;

/**
 * Electronic
 *
 * Created by zhangxf on 12/1/2016.
 */
class Electronic implements AllKindsOfMusic {
    public final String artist;

    public Electronic(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Electronic{" +
                "artist='" + artist + '\'' +
                '}';
    }
}
