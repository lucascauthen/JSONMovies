package com.example.jsonmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieSummary implements Parcelable {
    private final int rank;
    private final String title;
    private final int year;
    private final String imbdId;
    private final String imbdRating;
    private final int imbdVotes;
    private final String posterUrl;
    private final String imbdLink;

    public MovieSummary(int rank, String title, int year, String imbdId, String imbdRating, int imbdVotes, String posterUrl, String imbdLink) {
        this.rank = rank;
        this.title = title;
        this.year = year;
        this.imbdId = imbdId;
        this.imbdRating = imbdRating;
        this.imbdVotes = imbdVotes;
        this.posterUrl = posterUrl;
        this.imbdLink = imbdLink;
    }

    public int getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
    public String getImbdId() {
        return imbdId;
    }

    public String getImbdRating() {
        return imbdRating;
    }

    public int getImbdVotes() {
        return imbdVotes;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getImbdLink() {
        return imbdLink;
    }

    protected MovieSummary(Parcel in) {
        rank = in.readInt();
        title = in.readString();
        year = in.readInt();
        imbdId = in.readString();
        imbdRating = in.readString();
        imbdVotes = in.readInt();
        posterUrl = in.readString();
        imbdLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeString(title);
        dest.writeInt(year);
        dest.writeString(imbdId);
        dest.writeString(imbdRating);
        dest.writeInt(imbdVotes);
        dest.writeString(posterUrl);
        dest.writeString(imbdLink);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieSummary> CREATOR = new Parcelable.Creator<MovieSummary>() {
        @Override
        public MovieSummary createFromParcel(Parcel in) {
            return new MovieSummary(in);
        }

        @Override
        public MovieSummary[] newArray(int size) {
            return new MovieSummary[size];
        }
    };
}