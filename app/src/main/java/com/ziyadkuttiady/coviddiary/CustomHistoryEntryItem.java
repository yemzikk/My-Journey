package com.ziyadkuttiady.coviddiary;

public class CustomHistoryEntryItem implements ItemHistory{
    public final String title,time;

    public CustomHistoryEntryItem(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}