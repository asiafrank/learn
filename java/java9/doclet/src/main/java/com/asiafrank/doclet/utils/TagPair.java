package com.asiafrank.doclet.utils;

public class TagPair {
    public String tagStart;

    public char tagEnd;

    public String replaceStart;

    public String replaceEnd;

    public TagPair(String tagStart, char tagEnd, String replaceStart, String replaceEnd) {
        this.tagStart = tagStart;
        this.tagEnd = tagEnd;
        this.replaceStart = replaceStart;
        this.replaceEnd = replaceEnd;
    }

    @Override
    public String toString() {
        return tagStart + tagEnd;
    }
}