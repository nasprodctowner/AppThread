package fr.miage.td1.appthread.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.miage.td1.appthread.ChangeCoverAsyncTaskS;
import fr.miage.td1.appthread.MainActivity;
import fr.miage.td1.appthread.MovieAdapter;
import fr.miage.td1.appthread.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieDataAsyncTask extends AsyncTask<String, Void, Movie> {

    private WeakReference<MovieAdapter> movieAdapterWeakReference;
    private static final String API_KEY = "ffa399b8";
    private String title;
    private String director;
    private String producer;
    private String year;
    private Movie movie;
    private String poster;
    private List<Movie> movieList;



    public GetMovieDataAsyncTask(MovieAdapter movieAdapter) {
        this.movieAdapterWeakReference = new WeakReference<MovieAdapter>(movieAdapter);
    }

    @Override
    protected Movie doInBackground(String... strings) {


        return getMoviesFromOmdb(strings[0]);


    }


    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);

        MovieAdapter movieAdapter = movieAdapterWeakReference.get();

        movieList = new ArrayList<>();

        if(movieAdapter != null){
            movieAdapter.notifyDataSetChanged();
        }


    }



    private Movie getMoviesFromOmdb(String id){
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        Call<Movie> call = movieDataService.getMovie(id, API_KEY);

        movie = new Movie();


        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                title = response.body().getName();
                director = response.body().getDirector();
                producer = response.body().getProducer();
                year = response.body().getYear();
                poster = response.body().getPoster();

                movie.setName(title);
                movie.setDirector(director);
                movie.setProducer(producer);
                movie.setYear(year);

                MovieAdapter movieAdapter = movieAdapterWeakReference.get();

                ChangeCoverAsyncTaskS c = new ChangeCoverAsyncTaskS(movieAdapter,movie);
                c.execute(poster);

                try {
                    Bitmap bitmap = c.get();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    movie.setImageByte(byteArrayOutputStream.toByteArray());
                    byte[] s = movie.getImageByte();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                movie.save();

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        return movie;
    }

}
