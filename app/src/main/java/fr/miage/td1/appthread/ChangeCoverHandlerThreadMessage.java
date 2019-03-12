package fr.miage.td1.appthread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class ChangeCoverHandlerThreadMessage implements Handler.Callback {

    private HandlerThread handlerThread;
    public static final int CHANGE_COVER = 1;



    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what == CHANGE_COVER ){
            MovieData movieData = (MovieData) msg.obj ;
            ChangeCoverRunnableThread changeCoverRunnableThread = new ChangeCoverRunnableThread
                    (movieData.getMovieAdapter(),movieData.getMovie(),movieData.getHandlerUI(),movieData.getUrl());

            changeCoverRunnableThread.run();
        }
        return false;
    }
}
