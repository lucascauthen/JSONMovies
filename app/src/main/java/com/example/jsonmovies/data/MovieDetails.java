package com.example.jsonmovies.data;

import android.text.TextUtils;

import java.util.List;

public class MovieDetails {
    private final MovieSummary summary;
    private final String rated;
    private final String released;
    private final String runtime;
    private final List<String> genres;
    private final String director;
    private final String writer;
    private final List<String> actors;
    private final String plot;
    private final List<String> languages;
    private final String country;
    private final String awards;
    private final String metaScore;

    public MovieDetails(MovieSummary summary, String rated, String released, String runtime, List<String> genres, String director, String writer, List<String> actors, String plot, List<String> languages, String country, String awards, String metaScore) {
        this.summary = summary;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genres = genres;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.languages = languages;
        this.country = country;
        this.awards = awards;
        this.metaScore = metaScore;
    }

    public MovieSummary getSummary() {
        return summary;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return listToString(genres);
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return listToString(actors);
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguages() {
        return listToString(languages);
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getMetaScore() {
        return metaScore;
    }

    private String listToString(List<String> list) {
        return TextUtils.join(",", list); //Yes I know this is android stuff in the data layer, but I was too lazy to write this myself
    }
}
