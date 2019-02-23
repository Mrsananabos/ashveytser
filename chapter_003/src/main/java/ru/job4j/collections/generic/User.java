package ru.job4j.collections.generic;

public class User extends Base {
    protected User(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "User{" + "id: " + getId() + "}";
    }
}
