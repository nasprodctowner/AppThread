package fr.miage.td1.appthread.network;

import java.util.List;

import fr.miage.td1.appthread.model.Movie;
import fr.miage.td1.appthread.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetMovieDataService {


    @GET(".")
    Call<Movie> getMovie(@Query("i") String id, @Query("apiKey") String userkey);

    @GET(".")
    Call<MovieList> getMoviesSearched(@Query("s") String title, @Query("apiKey") String userkey);
}
