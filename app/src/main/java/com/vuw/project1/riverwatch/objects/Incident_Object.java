package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 23/07/2016.
 */
public class Incident_Object {

    public long id;
    public String name;
    public String description;
    public String image;

    public Incident_Object(long id, String name, String description, String image){
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
