package com.ziyadkuttiady.coviddiary;

public class CustomHistorySectionItem implements ItemHistory {

    private final String title, id;

    public CustomHistorySectionItem(String title, String id) {
        this.title = title;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}