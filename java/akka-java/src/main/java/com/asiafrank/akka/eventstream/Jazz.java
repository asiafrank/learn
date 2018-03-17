package com.asiafrank.akka.eventstream;

/**
 * Jazz
 *
 * Created by zhangxf on 12/1/2016.
 */
class Jazz implements AllKindsOfMusic {
    public final String artist;

    public Jazz(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Jazz{" +
                "artist='" + artist + '\'' +
                '}';
    }
}
