package com.ziyadkuttiady.coviddiary;

public class CustomHistoryEntryItem implements ItemHistory {
    public final String title, time, id;

    public CustomHistoryEntryItem(String title, String time, String id) {
        this.title = title;
        this.time = time;
        this.id = id;
    }

    public String getId() {
        return id;
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