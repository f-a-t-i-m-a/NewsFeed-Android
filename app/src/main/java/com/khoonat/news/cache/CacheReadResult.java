package com.khoonat.news.cache;

public class CacheReadResult {
    private String contents;
    private long ageMillis;

    public CacheReadResult(String contents, long ageMillis) {
        this.contents = contents;
        this.ageMillis = ageMillis;
    }

    public String getContents() {
        return contents;
    }

    public long getAgeMillis() {
        return ageMillis;
    }
}
