package com.example.jsonmovies.presenter;

import android.util.Log;
import android.widget.Toast;
import com.example.jsonmovies.data.MovieSummary;
import com.example.jsonmovies.util.BackgroundExecutor;
import com.example.jsonmovies.util.ForegroundExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieListPresenter implements Presenter<MovieListPresenter.MovieListView>{

    private final BackgroundExecutor backgroundExecutor;
    private final ForegroundExecutor foregroundExecutor;
    private final OkHttpClient okHttpClient;

    private List<MovieSummary> movies;
    private MovieListView view = NULL_VIEW;
    private static final MovieListView NULL_VIEW = new NullMovieListView();
    private static final String BASE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/master/top_movies.json";

    public MovieListPresenter(BackgroundExecutor backgroundExecutor, ForegroundExecutor foregroundExecutor, OkHttpClient okHttpClient) {
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        this.okHttpClient = okHttpClient;
    }

    public MovieSummary getSummary(int id) {
        return movies.get(id);
    }

    @Override
    public void attachView(final MovieListView view) {
        this.view = view;
        view.showLoading();
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loadData();
                foregroundExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();
                        view.setData(movies);
                    }
                });
            }
        });
    }

    private void loadData() {
        this.movies = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray list = new JSONArray(jsonData);
            for(int i = 0; i < list.length(); i++) {
                JSONObject curObject = list.getJSONObject(i);
                int rank = curObject.getInt("rank");
                String title = curObject.getString("title");
                int year = curObject.getInt("year");
                String imbdId = curObject.getString("imdbId");
                String imbdRating = String.valueOf(curObject.getDouble("imdbRating"));
                int imbdVotes = curObject.getInt("imdbVotes");
                String poster = curObject.getString("poster");
                String imbdLink = curObject.getString("imdbLink");
                MovieSummary newSummary = new MovieSummary(rank, title, year, imbdId, imbdRating, imbdVotes, poster, imbdLink);
                movies.add(newSummary);
            }
        } catch (IOException e) {
            e.printStackTrace();
            foregroundExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    view.showError("Unable to retrieve data from the server.");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachView() {
        this.view = NULL_VIEW;
    }

    public interface MovieListView {
        void setData(List<MovieSummary> data);
        void showLoading();
        void hideLoading();
        void showError(String msg);
    }
    public static class NullMovieListView implements MovieListView {

        @Override
        public void setData(List<MovieSummary> data) {

        }

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void showError(String msg) {

        }
    }
}
