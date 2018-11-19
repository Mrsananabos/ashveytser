package ru.job4j.cinema.model.service;

import org.apache.log4j.Logger;
import ru.job4j.cinema.model.CinemaSize;
import ru.job4j.cinema.model.Site;
import ru.job4j.cinema.model.persistence.DBStoreHall;
import ru.job4j.cinema.model.persistence.StoreCinema;

import java.util.ArrayList;
import java.util.Collection;

public class ValidateService implements Validate {
    private static final Validate INSTANCE = new ValidateService();
    private final StoreCinema store = DBStoreHall.getInstance();
    private static final Logger logger = Logger.getLogger(ValidateService.class);

    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Site> getAll() {
        Collection<Site> result = new ArrayList<>();
        result = store.getAll();
        if (result.isEmpty()) {
            logger.error("Tickets not found!!!");
        }
        return result;
    }

    @Override
    public CinemaSize getCinemaSize() {
        CinemaSize result = new CinemaSize(0, 0);
        result = store.getCinemaSize();
        if (result.getPlace() == 0 || result.getRow() == 0) {
            logger.error("Wrong hall size!!!");
        }
        return result;
    }

    @Override
    public boolean reservePlace(int id, String username, String phone) {
        boolean result = false;
        if (id == 0 || username.isEmpty() || phone.isEmpty()) {
            logger.info("");
        } else {
            result = store.reservePlace(id, username, phone);
        }
        return result;
    }
}
