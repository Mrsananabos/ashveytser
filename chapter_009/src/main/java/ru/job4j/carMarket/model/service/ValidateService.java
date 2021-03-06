package ru.job4j.carMarket.model.service;

import org.apache.log4j.Logger;
import ru.job4j.carMarket.model.dao.HiberStorage;
import ru.job4j.carMarket.model.dao.Storage;
import ru.job4j.carMarket.model.entity.Car;
import ru.job4j.carMarket.model.entity.FormCarSale;
import ru.job4j.carMarket.model.entity.User;

import java.util.List;

public class ValidateService implements Validate {
    private static final Validate INSTANCE = new ValidateService();
    private static final Logger LOGGER = Logger.getLogger(ValidateService.class);
    private final Storage storage = HiberStorage.getInstance();

    private ValidateService() {
    }

    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Car> findCarsByKey(String key) {
        List<Car> result = null;
        if (isValid(key)) {
            if ("all".equals(key)) {
                result = storage.getCars();
            } else {
                User user = storage.findUserByLogin(key);
                result = user.getCars();
            }
            if (result == null) {
                LOGGER.info("Cars with key(" + key + ") are not found");
            }
        } else {
            LOGGER.info("Key is not valid");
        }
        return result;
    }

    @Override
    public List findPartsCarByKey(String key) {
        List result = null;
        if (isValid(key)) {
            switch (key) {
                case ("mark"): {
                    result = storage.getMarks();
                    break;
                }
                case ("trans"): {
                    result = storage.getTransmission();
                    break;
                }
                case ("body"): {
                    result = storage.getBodyType();
                    break;
                }
                default:
                    LOGGER.info(String.format("key %s is not exist", key));
            }
            if (result == null) {
                LOGGER.info(String.format("Parts of %s are not found", key));
            }
        } else {
            LOGGER.info("Key is not valid");
        }
        return result;
    }

    @Override
    public List findModelsByMark(String idMark) {
        List result = null;
        if (isValid(idMark)) {
            result = storage.findModelsByMark(Integer.valueOf(idMark));
            if (result == null) {
                LOGGER.info(String.format("Models of idMark(%s) are not found", idMark));
            }
        } else {
            LOGGER.info("Id of mark is not valid");
        }
        return result;
    }

    @Override
    public Car addCarToUser(FormCarSale formCar) {
        if (formCar != null) {
            if (isValid(formCar.getAuthor())) {
                User user = storage.findUserByLogin(formCar.getAuthor());
                storage.addCarToUser(user, formCar.getCar());
            } else {
                LOGGER.info("Car does't have author");
            }
        } else {
            LOGGER.info("Car is empty");
        }
        return null;
    }

    @Override
    public boolean addUser(String login, String password) {
        boolean result = false;
        if (isValid(login) && isValid(password)) {
            User user = storage.findUserByLogin(login);
            if (user == null) {
                User newUser = new User();
                newUser.setLogin(login);
                newUser.setPassword(password);
                storage.addUser(newUser);
                result = true;
            } else {
                LOGGER.info("User with login(" + login + ") is already exists");
            }
        } else {
            LOGGER.info("Data of user are not valid");
        }
        return result;
    }

    @Override
    public User findUserByLogin(String login) {
        User result = null;
        if (isValid(login)) {
            result = storage.findUserByLogin(login);
            if (result == null) {
                LOGGER.info("User with login(" + login + ") is not found");
            }
        } else {
            LOGGER.info("login is not valid");
        }
        return result;
    }

    @Override
    public void soldCar(String id) {
        if (isValid(id)) {
            int idCar = Integer.valueOf(id);
            storage.soldCar(idCar);
        } else {
            LOGGER.info("Car's id is not valid");
        }
    }

    private boolean isValid(String value) {
        return ((value != null) && (!value.isEmpty()));
    }


}
