package com.example.musika.Search;

import java.io.Serializable;

public class Search implements Serializable {
    private String artist, liked, poster, song, style, url, viewed;

    public Search(String artist, String liked, String poster, String song, String style, String url, String viewed) {
        this.artist = artist;
        this.liked = liked;
        this.poster = poster;
        this.song = song;
        this.style = style;
        this.url = url;
        this.viewed = viewed;
    }
    public Search() {}

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }
}
