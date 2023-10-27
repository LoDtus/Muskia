package com.example.musika.Home;

import java.io.Serializable;

public class Song implements Serializable {
    private String songID, poster, nameSong, artist;

    public Song(String songID, String poster, String nameSong, String artist) {
        this.songID = songID;
        this.poster = poster;
        this.nameSong = nameSong;
        this.artist = artist;
    }
    public Song() {}

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
