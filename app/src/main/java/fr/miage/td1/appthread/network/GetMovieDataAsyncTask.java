package fr.miage.td1.appthread.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.miage.td1.appthread.ChangeCoverAsyncTaskS;
import fr.miage.td1.appthread.ChangeCoverForMovieAsyncTask;
import fr.miage.td1.appthread.MainActivity;
import fr.miage.td1.appthread.MovieAdapter;
import fr.miage.td1.appthread.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieDataAsyncTask extends AsyncTask<String, Void, Void> {

    private WeakReference<MovieAdapter> movieAdapterWeakReference;
    private WeakReference<List<Movie>> listWeakReference;
    private static final String API_KEY = "ffa399b8";
    private String title;
    private String director;
    private String producer;
    private String year;
    private Movie movie;
    private String poster;
    private List<Movie> movieList;
    private GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
    private  MovieAdapter movieAdapter;



    public GetMovieDataAsyncTask(MovieAdapter movieAdapter, List<Movie> movies) {
        this.movieAdapterWeakReference = new WeakReference<MovieAdapter>(movieAdapter);
        this.listWeakReference = new WeakReference<List<Movie>>(movies);
}

    @Override
    protected Void doInBackground(String... strings) {


         getMoviesFromOmdb(strings[0]);
         return null;


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        List<Movie> movies = listWeakReference.get();

        if(movieAdapter != null){
            byte[] s = movie.getImageByte();
            movies.add(movie);
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void getMoviesFromOmdb(String id){

        Call<Movie> call = movieDataService.getMovie(id, API_KEY);

        movie = new Movie();

        try {
            movie = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }


        movieAdapter = movieAdapterWeakReference.get();
        ChangeCoverForMovieAsyncTask c = new ChangeCoverForMovieAsyncTask(movie,movieAdapter);

        c.execute(movie);

        movie.save();


    }

}
