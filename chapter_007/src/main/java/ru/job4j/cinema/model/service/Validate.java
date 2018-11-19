package ru.job4j.cinema.model.service;

import ru.job4j.cinema.model.CinemaSize;
import ru.job4j.cinema.model.Site;

import java.util.Collection;

public interface Validate {
    Collection<Site> getAll();
    CinemaSize getCinemaSize();
    boolean reservePlace(int id, String username, String phone);
}
