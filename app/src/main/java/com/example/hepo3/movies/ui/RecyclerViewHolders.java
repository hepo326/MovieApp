package com.example.hepo3.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.hepo3.movies.model.MovieList;
import com.example.hepo3.movies.R;

import java.util.ArrayList;

/**
 * Created by hepo3 on 4/2/2017.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements OnClickListener {

    public TextView movieTitle;
    public SquareImageView movieImage;
    ArrayList<MovieList> selectedMovie;
    public static MovieList movie;

    public RecyclerViewHolders(View itemView, ArrayList<MovieList> selectedMovie) {
        super(itemView);
        itemView.setOnClickListener(this);
        movieTitle = (TextView) itemView.findViewById(R.id.movie_title_display);
        movieImage = (SquareImageView) itemView.findViewById(R.id.imageview);
        this.selectedMovie = selectedMovie;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        movie = selectedMovie.get(getAdapterPosition());
        Intent detailsIntent = new Intent(context,DetailsActivity.class);
        detailsIntent.putExtra("selectedMovie",movie);
        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(detailsIntent);
    }
}
