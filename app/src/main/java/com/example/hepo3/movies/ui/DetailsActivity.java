package com.example.hepo3.movies.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hepo3.movies.R;
import com.example.hepo3.movies.model.MovieList;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.movie_poster)
    SquareImageView img;
    @BindView(R.id.movie_overview)
    TextView nDescriptionTextView;
    @BindView(R.id.movie_releaseDate)
    TextView nReleaseDateTextView;
    @BindView(R.id.movie_title)
    TextView nTitleTextView;
    @BindView(R.id.movie_voteAvg)
    TextView nVoteAvgTextView;

    MovieList movieItem;

    int id;
    String title;
    String poster;
    String description;
    String release_date;
    String voteAVG;


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("selectedMovie", movieItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movieItem = (MovieList) getIntent().getSerializableExtra("selectedMovie");
        ButterKnife.bind(this);
        id = movieItem.getId();
        title = movieItem.getTitle();
        poster = movieItem.getPoster();
        description = movieItem.getDescription();
        release_date = movieItem.getRelease_date();
        voteAVG = movieItem.getVoteAVG();

        String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/" + poster;

        nTitleTextView.setText(title);
        Picasso.with(this).load(POSTER_BASE_URL).into(img);
        nDescriptionTextView.setText(description);
        nReleaseDateTextView.setText(release_date);
        nVoteAvgTextView.setText(voteAVG + "/10");


    }
}
