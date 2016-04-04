package com.example.jsonmovies.presenter;

import com.example.jsonmovies.data.MovieDetails;
import com.example.jsonmovies.data.MovieSummary;
import com.example.jsonmovies.util.BackgroundExecutor;
import com.example.jsonmovies.util.ForegroundExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MovieDetailsPresenter implements Presenter<MovieDetailsPresenter.MovieDetailsView>{

    private final BackgroundExecutor backgroundExecutor;
    private final ForegroundExecutor foregroundExecutor;
    private final OkHttpClient okHttpClient;
    private static final String BASE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/master/by_id/";

    private static final MovieDetailsView NULL_VIEW = new NullMovieDetailsView();
    private MovieDetailsView view = NULL_VIEW;
    private MovieSummary summary;
    private MovieDetails details;
    private boolean needUpdate = false;

    public MovieDetailsPresenter(BackgroundExecutor backgroundExecutor, ForegroundExecutor foregroundExecutor, OkHttpClient okHttpClient) {
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void attachView(final MovieDetailsView view) {
        this.view = view;
        if(needUpdate) {
            view.showLoading();
            backgroundExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    loadData();
                    foregroundExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            view.hideLoading();
                            view.setData(details);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void detachView() {
        this.view = NULL_VIEW;
    }

    public void setData(MovieSummary summary) {
        this.summary = summary;
        needUpdate = true;
    }

    private void loadData() {
        Request request = new Request.Builder()
                .url(BASE_URL + summary.getImbdId() + ".json")
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject object = new JSONObject(jsonData);
            String rated = object.getString("rated");
            String released = object.getString("released");
            String runtime = object.getString("runtime");
            List<String> genre = convertToList(object.getJSONArray("genre"));
            String director = object.getString("director");
            String writer = object.getString("writer");
            List<String> actors = convertToList(object.getJSONArray("actors"));
            String plot = object.getString("plot");
            List<String> language = convertToList(object.getJSONArray("language"));
            String country = object.getString("country");
            String awards = object.getString("awards");
            String metascore = object.getString("metascore");
            this.details = new MovieDetails(summary, rated, released, runtime, genre,
                                            director, writer, actors, plot, language,
                                            country, awards, metascore);
        } catch (IOException e) {
            e.printStackTrace();
            foregroundExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    view.showError("Unable to get data from the server");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private List<String> convertToList(JSONArray list) {
        List<String> stringList = new ArrayList<>();
        for(int i = 0; i < list.length(); i++) {
            try {
                stringList.add((String)list.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

    public interface MovieDetailsView {
        void showLoading();
        void hideLoading();
        void setData(MovieDetails details);
        void showError(String msg);
    }

    public static class NullMovieDetailsView implements MovieDetailsView{

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void setData(MovieDetails details) {

        }

        @Override
        public void showError(String msg) {

        }
    }
}
