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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (idPlace != person.idPlace) return false;
        if (username != null ? !username.equals(person.username) : person.username != null) return false;
        return phone != null ? phone.equals(person.phone) : person.phone == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + idPlace;
        return result;
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
