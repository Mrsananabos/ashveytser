package ru.job4j.cinema.model;

public class CinemaSize {
    private int row;
    private int place;

    public CinemaSize(int row, int place) {
        this.row = row;
        this.place = place;
    }

    public int getRow() {
        return row;
    }

    public int getPlace() {
        return place;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "CinemaSize{" +
                "row=" + row +
                ", place=" + place +
                '}';
    }
}
