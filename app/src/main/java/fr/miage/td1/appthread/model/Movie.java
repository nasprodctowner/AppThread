package fr.miage.td1.appthread.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;

@Table(name = "Movie")
public class Movie extends SugarRecord implements Serializable {

    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Title")
    private String name;
    @SerializedName("Director")
    private String director;
    @SerializedName("Production")
    private String producer;
    @SerializedName("Year")
    private String year;
    @SerializedName("Poster")
    private String poster;

    private transient Bitmap image;
    private byte[] imageByte;

    public Movie(String name, String director, String producer, String year, Bitmap image) {

        this.name = name;
        this.director = director;
        this.producer = producer;
        this.year = year;
        this.image = image;
    }

    public Movie() {
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}