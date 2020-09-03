package com.make.model;

public class MoviePreView {

    private String posterPath;
    private String title;
    private String date;
    private String overview;

    public MoviePreView() {
    }

    public MoviePreView(String posterPath, String title, String date, String overview) {
        this.posterPath = posterPath;
        this.title = title;
        this.date = date;
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "MoviePreView{" +
                "posterPath='" + posterPath + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
