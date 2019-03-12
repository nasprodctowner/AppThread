package fr.miage.td1.appthread;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.Adapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.miage.td1.appthread.model.Movie;

public class ChangeCoverRunnableThread implements Runnable {


    private WeakReference<MovieAdapter> wRAdapter ; //WeakReference
    private Movie movie;
    private String url;
    private Handler handlerUI;

    public ChangeCoverRunnableThread(MovieAdapter adapter, Movie movie, Handler handlerUI, String url) {
        this.wRAdapter = new WeakReference<MovieAdapter>(adapter);
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

             final MovieAdapter adapter = wRAdapter.get(); //Strong reference


             if (adapter != null) {
                 handlerUI.post(new Runnable() {
                     @Override
                     public void run() {
                         adapter.notifyDataSetChanged();
                     }
                 });
             }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
