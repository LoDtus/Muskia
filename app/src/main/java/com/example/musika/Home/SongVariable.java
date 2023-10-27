package com.example.musika.Home;

public class SongVariable {
    public static String poster, nameSong, nameArtist;

    public SongVariable(String poster, String nameSong, String nameArtist) {
        this.poster = poster;
        this.nameSong = nameSong;
        this.nameArtist = nameArtist;
    }
    public SongVariable() {

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

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }
}
