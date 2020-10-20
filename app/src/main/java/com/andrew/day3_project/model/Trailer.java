package com.andrew.day3_project.model;

public class Trailer {
    private String id,iso_639_1,iso_3166_1,site,type;
    private String key;
    private String name;
    private int size;

    public Trailer() {
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
