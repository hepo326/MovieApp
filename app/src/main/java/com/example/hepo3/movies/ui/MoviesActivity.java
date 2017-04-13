package com.example.hepo3.movies.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hepo3.movies.adapter.MoviesAdapter;
import com.example.hepo3.movies.model.FetchMovies;
import com.example.hepo3.movies.model.MovieList;
import com.example.hepo3.movies.R;

import java.util.ArrayList;

import static com.example.hepo3.movies.ui.RecyclerViewHolders.movie;

public class MoviesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoviesAdapter moviesAdapter;
    ArrayList<MovieList> itemList;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //FetchMovies
    public void updateMovies() {

        moviesAdapter.clear();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortType = sharedPrefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_pop_value));

        FetchMovies fetch = new FetchMovies(this, itemList, moviesAdapter);
        fetch.execute(sortType);


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Check connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Connection Alert Dialog
    public void displayAlert() {
        new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        })
                .show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Check connectivity inorder to load movies or give the user an alert
    @Override
    public void onStart() {
        super.onStart();
        if (isNetworkAvailable()) {
            updateMovies();
        } else {
            displayAlert();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Recycler view , adapter and arraylist declarations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        itemList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        moviesAdapter = new MoviesAdapter(itemList, getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //View the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // OptionMenu action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        } else if (id == R.id.action_settings) {

            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //numberOfColumns of the Recycler view
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

}
