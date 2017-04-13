package com.example.hepo3.movies.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.hepo3.movies.adapter.MoviesAdapter;
import com.example.hepo3.movies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Moheb Badawy on 8/30/2016.
 */
public class FetchMovies extends AsyncTask<String, Void, Boolean> {

    ProgressDialog progDialog;
    String movieJsonStr;
    Context c;
    ArrayList<MovieList> arrayList;
    MoviesAdapter adapter;

    public FetchMovies(Context c, ArrayList<MovieList> arrayList, MoviesAdapter adapter ){
        this.c=c;
        this.arrayList=arrayList;
        this.adapter=adapter;
    }

    private final String LOG_TAG = FetchMovies.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDialog= ProgressDialog.show(c, c.getString(R.string.progressIndicator_title), c.getString(R.string.progressIndicator_message));


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100000);
                    progDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected Boolean doInBackground(String... params) {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {


            final String MOVIES_BASE_URL =
                    "http://api.themoviedb.org/3/movie/";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APPID_PARAM, c.getString(R.string.MOVIE_DB_API_KEY))
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return false;
            }
            movieJsonStr = buffer.toString();
            getMoviesFromJson(movieJsonStr);
            return true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();

            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return false;
    }



    private void getMoviesFromJson(String moviesJsonStr)
            throws JSONException {

        final String MOVIE_RESULTS = "results";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_DESC = "overview";
        final String MOVIE_REALEASE_DATE = "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_VOTE_AVG = "vote_average";

        JSONObject movieJsonStr = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = movieJsonStr.getJSONArray(MOVIE_RESULTS);

        for (int i = 0; i < moviesArray.length(); i++) {

            int movie_id;
            String title;
            String poster;
            String description;
            String release_date;
            String voteAVG;

            JSONObject moviesDetails = moviesArray.getJSONObject(i);

            movie_id = moviesDetails.getInt(MOVIE_ID);
            title = moviesDetails.getString(MOVIE_TITLE);
            poster = moviesDetails.getString(MOVIE_POSTER);
            description = moviesDetails.getString(MOVIE_DESC);
            release_date = moviesDetails.getString(MOVIE_REALEASE_DATE);
            voteAVG = moviesDetails.getString(MOVIE_VOTE_AVG);

            arrayList.add(new MovieList(movie_id,title,poster,description,release_date,voteAVG));

        }


    }


    @Override
    protected void onPostExecute(Boolean flag) {

        if(flag){

            adapter.setArrayData(arrayList);


        }else{

            Toast.makeText(c, "Failed to fetch movies.", Toast.LENGTH_SHORT).show();

        }

        if(progDialog!=null)
            progDialog.dismiss();

    }
}