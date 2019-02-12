package fr.miage.td1.appthread.model;

import android.graphics.Bitmap;

public class Movie {
    private String name;
    private String director;
    private String producer;
    private String year;
    private Bitmap image;

    public Movie(String name, String director, String producer, String year, Bitmap image) {
        this.name = name;
        this.director = director;
        this.producer = producer;
        this.year = year;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}