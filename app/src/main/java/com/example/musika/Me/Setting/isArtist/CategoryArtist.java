package com.example.musika.Me.Setting.isArtist;

public class CategoryArtist {
    private String nameCategory;

    public CategoryArtist(String nameCategory) {
        this.nameCategory = nameCategory;
    }
    public CategoryArtist() {

    } //============================================================================================

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
