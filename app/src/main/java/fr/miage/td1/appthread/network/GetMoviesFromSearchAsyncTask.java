package fr.miage.td1.appthread.network;

import android.os.AsyncTask;
import android.widget.Adapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import fr.miage.td1.appthread.ChangeCoverForMovieAsyncTask;
import fr.miage.td1.appthread.MovieAdapter;
import fr.miage.td1.appthread.model.Movie;
import fr.miage.td1.appthread.model.MovieList;
import retrofit2.Call;

public class GetMoviesFromSearchAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
    private static final String API_KEY = "ffa399b8";
    private WeakReference<MovieAdapter> movieAdapterWeakReference;
    private WeakReference<List<Movie>> listWeakReference;

    public GetMoviesFromSearchAsyncTask(List<Movie> movies, MovieAdapter movieAdapter) {

        this.movieAdapterWeakReference = new WeakReference<MovieAdapter>(movieAdapter);
        this.listWeakReference = new WeakReference<List<Movie>>(movies);
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {

        getMoviesByTagName(strings[0]);
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);

        MovieAdapter movieAdapter = movieAdapterWeakReference.get();

        if(movieAdapter != null){
            movieAdapter.notifyDataSetChanged();
        }

    }

    private void getMoviesByTagName(String tagName){

        Call<MovieList> call = movieDataService.getMoviesSearched(tagName, API_KEY);

        MovieList moviesSearched ;
        List<Movie> movies = listWeakReference.get();

        try {
            moviesSearched = call.execute().body();

            if(moviesSearched != null){
                movies.clear();
                movies.addAll(moviesSearched.getMovieList());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
