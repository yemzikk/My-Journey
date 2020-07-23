package com.ziyadkuttiady.coviddiary;

public class CustomHistorySectionItem implements ItemHistory {

    private final String title;

    public CustomHistorySectionItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}