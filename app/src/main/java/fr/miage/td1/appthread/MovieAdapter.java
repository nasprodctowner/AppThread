package fr.miage.td1.appthread;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.miage.td1.appthread.model.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {

    //tweets est la liste des models à afficher
    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_movie,parent, false);
        }

        MovieViewHolder viewHolder = (MovieViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MovieViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.director = (TextView) convertView.findViewById(R.id.director);
            viewHolder.producer = (TextView) convertView.findViewById(R.id.producer);
            viewHolder.year = (TextView) convertView.findViewById(R.id.year);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Movie> movies
        Movie movie = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(movie.getName());
        viewHolder.director.setText(movie.getDirector());
        viewHolder.producer.setText(movie.getProducer());
        viewHolder.year.setText(movie.getYear());
        if (movie.getImage() != null) viewHolder.image.setImageBitmap(movie.getImage());
        else {
            viewHolder.image.setImageResource(R.mipmap.movie);
        }

        return convertView;
    }

    private class MovieViewHolder{
        public TextView name;
        public TextView director;
        public TextView producer;
        public TextView year;
        public ImageView image;
    }
}