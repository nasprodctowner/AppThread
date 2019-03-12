package fr.miage.td1.appthread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import fr.miage.td1.appthread.model.Movie;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ImageView image;
    Button asyncs;
    Button asyncp;
    Button handlerT;
    Button thread;
    Button poolThread;
    Button messageThread;
    String url = "https://lorempixel.com/400/400/";
    MovieAdapter adapter;
    List<Movie> movies ;
    Handler handlerUI = new Handler(Looper.getMainLooper());
    HandlerThread handlerThread = new HandlerThread("HandlerThreadMovie");
    Handler handlerMovie ;
    Handler handlerMovieMessage;
    HandlerThread handlerThreadMessage = new HandlerThread("HandlerThreadMessageMovie");
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,100,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        handlerThread.start();
        handlerMovie = new Handler(handlerThread.getLooper());

        handlerThreadMessage.start();
        handlerMovieMessage = new Handler(handlerThreadMessage.getLooper(),new ChangeCoverHandlerThreadMessage());



        mListView = (ListView) findViewById(R.id.listMovies);

        movies = genererMovies();

        adapter = new MovieAdapter(MainActivity.this, movies);
        mListView.setAdapter(adapter);


        asyncs = (Button) findViewById(R.id.asyncTasks);
        asyncs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(Movie movie : movies){
                    ChangeCoverAsyncTaskS c = new ChangeCoverAsyncTaskS(adapter,movie);
                    c.execute(url);
                }
            }
        });

        asyncp = (Button) findViewById(R.id.asyncTaskp);
        asyncp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(Movie movie : movies){
                    ChangeCoverAsyncTaskS c = new ChangeCoverAsyncTaskS(adapter,movie);
                    c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
                }
            }
        });

        thread = (Button) findViewById(R.id.thread);
        thread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                for (Movie movie : movies) {
                  Thread thread = new Thread(new ChangeCoverRunnableThread(adapter,movie,handlerUI,url));
                  thread.start();
                }
            }
        });


        handlerT = (Button) findViewById(R.id.handlert_t);
        handlerT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (Movie movie : movies) {
                    handlerMovie.post(new ChangeCoverRunnableThread(adapter,movie,handlerUI,url));
                }
            }
        });

        poolThread = (Button) findViewById(R.id.poolThread);
        poolThread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (Movie movie : movies) {
                    threadPoolExecutor.execute(new ChangeCoverRunnableThread(adapter,movie,handlerUI,url));
                }
            }
        });

        messageThread = (Button) findViewById(R.id.handlert_m);
        messageThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Movie movie : movies) {
                    Message message = Message.obtain();
                    message.obj = new MovieData(adapter,movie,url,handlerUI);
                    message.what = ChangeCoverHandlerThreadMessage.CHANGE_COVER;
                    handlerMovieMessage.sendMessage(message);
                }
            }
        });

        }

    private List<Movie> genererMovies(){
        movies = new ArrayList<Movie>();
        int j = 1999;
        for (int i=1; i<6; i++){
            movies.add(new Movie("Film "+i,"Director "+i,"Producer "+i,""+j,null));
            j++;
        }
        return movies;
    }



}
