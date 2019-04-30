package fr.miage.td1.appthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.miage.td1.appthread.model.Movie;

public class ChangeCoverForMovieAsyncTask extends AsyncTask<Movie, Void, Void> {


    private Movie movie;
    private WeakReference<MovieAdapter> wMAdapter ;
    private Bitmap bitmap;

    public ChangeCoverForMovieAsyncTask(Movie movie, MovieAdapter movieAdapter) {
        this.movie = movie;
        this.wMAdapter = new WeakReference<MovieAdapter>(movieAdapter);
    }


    @Override
    protected Void doInBackground(Movie... movies) {
        getImage();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);



        if(bitmap != null){
            MovieAdapter movieAdapter = wMAdapter.get();

            if(movieAdapter != null){
                movieAdapter.notifyDataSetChanged();
            }
        }


    }

    private void getImage(){
        try {
            bitmap = downloadFromURL(movie.getPoster());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            movie.setImageByte(byteArrayOutputStream.toByteArray());
            movie.setImage(bitmap);

            movie.save();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap downloadFromURL(String url) throws IOException {
        URL urlImage = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlImage.openConnection();
        BufferedInputStream bufferedInputStream= new  BufferedInputStream(httpURLConnection.getInputStream());
        return BitmapFactory.decodeStream(bufferedInputStream);
    }


}
