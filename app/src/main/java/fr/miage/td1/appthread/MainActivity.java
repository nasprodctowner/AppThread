package fr.miage.td1.appthread;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import fr.miage.td1.appthread.model.Movie;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
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
    private static final int MY_PERMISSIONS_REQUEST_WRITE = 0;
    private static final int MY_PERMISSIONS_LOCATION = 1;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movies = new ArrayList<Movie>();

        isWriteStoragePermissionGranted();

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

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGPSPermissionGranted();
                movies.add(new Movie("GameOfThrones","John Snow","Denerys",""+2019,null));
                adapter = new MovieAdapter(MainActivity.this, movies);
                mListView.setAdapter(adapter);
            }
        });

        }

    private List<Movie> genererMovies(){
        int j = 1999;
        for (int i=1; i<6; i++){
            movies.add(new Movie("Film "+i,"Director "+i,"Producer "+i,""+j,null));
            j++;
        }
        return movies;
    }


    public void isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE);
                }
            }
        }
    }

    public void isGPSPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                         Toast.makeText(MainActivity.this, "ACCESS_FINE_LOCATION permission allows us to locate your app. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();

                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_LOCATION);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_LONG).show();
                    this.finish();
                    break;
                }

            }

            case MY_PERMISSIONS_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_LONG).show();
                    break;
                }
            }

        }
    }

}
