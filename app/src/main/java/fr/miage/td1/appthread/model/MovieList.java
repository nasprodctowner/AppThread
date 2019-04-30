package fr.miage.td1.appthread.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {

    @SerializedName("Search")
    private List<Movie> movieList;

    public MovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public MovieList() {
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
