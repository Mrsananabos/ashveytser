package ru.job4j.Sorting;

import javax.management.ObjectName;

public class User implements Comparable<User>{
    String name;
    int age;

    public User(int age, String name){
        this.name = name;
        this.age = age;
    }


    String getName() {
        return this.name;
    }

    int getAge() { return this.age; }


    @Override
    public int compareTo(User o) {
      return   Integer.compare(this.age, o.age);
    }

    @Override
    public String toString() {
        return "User{" + "name: " + name + ", age: " + age + "}";

    }

}