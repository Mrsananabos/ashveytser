package ru.job4j.cinema.model;

public class Site {
    private int id;
    private int row;
    private int place;
    private boolean isFree;
    private int cost;

    public Site(int id, int row, int place, boolean isFree, int cost) {
        this.id = id;
        this.row = row;
        this.place = place;
        this.isFree = isFree;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getPlace() {
        return place;
    }

    public boolean getisFree() {
        return isFree;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (id != site.id) return false;
        if (row != site.row) return false;
        if (place != site.place) return false;
        if (isFree != site.isFree) return false;
        return cost == site.cost;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + row;
        result = 31 * result + place;
        result = 31 * result + (isFree ? 1 : 0);
        result = 31 * result + cost;
        return result;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", row=" + row +
                ", place=" + place +
                ", isFree=" + isFree +
                ", cost=" + cost +
                '}';
    }
}
