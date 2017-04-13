package com.example.hepo3.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hepo3.movies.model.MovieList;
import com.example.hepo3.movies.R;
import com.example.hepo3.movies.ui.RecyclerViewHolders;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


/**
 * Created by hepo3 on 4/2/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<MovieList> movieItem;
    private Context context;

    public MoviesAdapter(ArrayList<MovieList> movieItem,Context context){
        this.movieItem=movieItem;
        this.context=context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout,null);
        RecyclerViewHolders recyclerViewHolders = new RecyclerViewHolders(view , movieItem);
        return recyclerViewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.movieTitle.setText(movieItem.get(position).getTitle());
        String POSTER_URL = movieItem.get(position).getPoster();
        String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/"+POSTER_URL;
//        Picasso.with(context).load(POSTER_BASE_URL).into(holder.movieImage);

        Picasso.with(context)
                .load(POSTER_BASE_URL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.pop05)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieItem.size();
    }

    public MovieList getMovie(){
        return RecyclerViewHolders.movie;
    }

    public void setArrayData(ArrayList<MovieList> movieItem){

        this.movieItem = movieItem ;
        notifyDataSetChanged();
    }

    public void clear(){

        movieItem.clear();
        notifyDataSetChanged();
    }
}

