package fr.miage.td1.appthread;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.miage.td1.appthread.model.Movie;
import fr.miage.td1.appthread.network.GetMoviesFromSearchAsyncTask;

public class SearchActivity extends AppCompatActivity {

    EditText tagName;
    Button submit;
    List<Movie> moviesSearched;
    MovieAdapter adapter;
    ListView mListView;
    String imdbID;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tagName = findViewById(R.id.tagName);
        submit = findViewById(R.id.submit);
        mListView = findViewById(R.id.listMoviesSearched);
        moviesSearched = new ArrayList<Movie>();
        adapter = new MovieAdapter(SearchActivity.this, moviesSearched);
        mListView.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moviesSearched.clear();
                GetMoviesFromSearchAsyncTask getMoviesFromSearchAsyncTask = new GetMoviesFromSearchAsyncTask(moviesSearched,adapter);
                getMoviesFromSearchAsyncTask.execute(tagName.getText().toString());

            }
        });


        //dialog
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.row_dialog_addmovie, null);

        final Button buttonSubmit = (Button) dialogView.findViewById(R.id.ok);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancel);
        textView = findViewById(R.id.idMovie);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

                Movie movie = (Movie) parent.getAdapter().getItem(position);
                imdbID = movie.getImdbID();

                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addFromSearch(view);

                        dialogBuilder.dismiss();
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // DO SOMETHINGS
                        dialogBuilder.dismiss();
                    }
                });
            }
        });
    }


    public void addFromSearch(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("imdbID", imdbID);
        startActivity(intent);
    }
}
