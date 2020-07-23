package com.ziyadkuttiady.coviddiary;

public class CustomHistoryEntryItem implements ItemHistory{
    public final String title;

    public CustomHistoryEntryItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}