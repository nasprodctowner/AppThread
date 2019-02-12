package fr.miage.td1.appthread;

import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.miage.td1.appthread.model.Movie;

public class ChangeCoverRunnableThread implements Runnable {

    private MovieAdapter adapter;
    private Movie movie;
    private String url;
    private Handler handlerUI;

    public ChangeCoverRunnableThread(MovieAdapter adapter, Movie movie, Handler handlerUI, String url) {
        this.adapter = adapter;
        this.movie = movie;
        this.url = url;
        this.handlerUI = handlerUI;
    }

    @Override
    public void run() {


        try {

            Thread.sleep(1000);

            URL urlImage = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlImage.openConnection();
            BufferedInputStream bufferedInputStream= new  BufferedInputStream(httpURLConnection.getInputStream());

            movie.setImage(BitmapFactory.decodeStream(bufferedInputStream));

            handlerUI.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
