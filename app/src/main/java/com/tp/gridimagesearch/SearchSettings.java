package com.tp.gridimagesearch;

import java.io.Serializable;

/**
 * Created by seshasa on 2/1/15.
 */
public class SearchSettings implements Serializable{
    public String query;
    public int page;
    public String type;
    public String size;
    public String color;
    public String site;
    public int pageSize = 8;

    public SearchSettings(String query, int page, String type, String size, String color, String site) {
        this.query = query;
        this.page = page;
        this.type = type;
        this.size = size;
        this.color = color;
        this.site = site;
    }

    public SearchSettings() {
        this.query = "";
        this.page = 0;
        this.type = "";
        this.size = "";
        this.color = "";
        this.site = "";
    }
}
