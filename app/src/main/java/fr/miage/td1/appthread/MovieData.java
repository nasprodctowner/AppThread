package fr.miage.td1.appthread;

import android.os.Handler;
import android.widget.Adapter;

import fr.miage.td1.appthread.model.Movie;

public class MovieData {
    private MovieAdapter movieAdapter;
    private Movie movie;
    private String url;
    private Handler handlerUI;



    public MovieData(MovieAdapter movieAdapter, Movie movie, String url, Handler handlerUI) {
        this.movieAdapter = movieAdapter;
        this.movie = movie;
        this.url = url;
        this.handlerUI = handlerUI;
    }

    public MovieAdapter getMovieAdapter() {
        return movieAdapter;
    }

    public void setMovieAdapter(MovieAdapter movieAdapter) {
        this.movieAdapter = movieAdapter;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Handler getHandlerUI() {
        return handlerUI;
    }

    public void setHandlerUI(Handler handlerUI) {
        this.handlerUI = handlerUI;
    }
}
