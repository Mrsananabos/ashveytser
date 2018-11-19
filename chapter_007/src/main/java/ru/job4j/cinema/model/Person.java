package ru.job4j.cinema.model;


public class Person {

    private String username;
    private String phone;
    private int idPlace;

    public Person() {
        super();
    }

    public Person(String username, String phone, int idPlace) {
        this.username = username;
        this.phone = phone;
        this.idPlace = idPlace;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public int getIdPlace() {
        return idPlace;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", idPlace=" + idPlace +
                '}';
    }
}
