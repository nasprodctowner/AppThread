package fr.miage.td1.appthread.network;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

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

        movie.setName(title);

        MovieAdapter movieAdapter = movieAdapterWeakReference.get();

        if(movieAdapter != null){
            movieAdapter.notifyDataSetChanged();
        }

    }



    private Movie getMoviesFromOmdb(String id){
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        Call<Movie> call = movieDataService.getMovie(id, API_KEY);

        final Movie movie = new Movie("cc","cc","cc","cc",null);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                title = response.body().getName();
                director = response.body().getDirector();
                producer = response.body().getProducer();
                year = response.body().getYear();

                movie.setName(title);
                movie.setDirector(director);
                movie.setProducer(producer);
                movie.setYear(year);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        return movie;
    }

}
