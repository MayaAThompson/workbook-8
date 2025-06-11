package com.pluralsight;

public class Film {

    int filmId;
    String title;
    String description;
    int releaseYear;
    int length;

    public Film(int filmId, String title, String description, int releaseYear, int length) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    @Override
    public String toString() {
        return ("\nFilm ID: " + this.filmId +
                "\nTitle: " + this.title +
                "\nDescription: " + this.description +
                "\nRelease Year: " + this.releaseYear +
                "\nLength: " + this.releaseYear);
    }
}
