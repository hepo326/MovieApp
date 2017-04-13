package com.example.hepo3.movies.model;

import java.io.Serializable;

/**
 * Created by hepo3 on 4/2/2017.
 */

public class MovieList implements Serializable {

    int id;
    String title;
    String poster;
    String description;
    String release_date;
    String voteAVG;

    public MovieList(int id, String title, String poster, String description, String release_date, String voteAVG) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.description = description;
        this.release_date = release_date;
        this.voteAVG = voteAVG;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVoteAVG() {
        return voteAVG;
    }
}
