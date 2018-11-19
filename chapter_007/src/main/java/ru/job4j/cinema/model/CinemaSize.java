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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CinemaSize that = (CinemaSize) o;

        if (row != that.row) return false;
        return place == that.place;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + place;
        return result;
    }

    @Override
    public String toString() {
        return "CinemaSize{" +
                "row=" + row +
                ", place=" + place +
                '}';
    }
}
