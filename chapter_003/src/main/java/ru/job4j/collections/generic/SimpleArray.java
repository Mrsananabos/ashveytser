package ru.job4j.collections.generic;

import java.util.Iterator;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] objects;
    protected int index = 0;
    private int size;

    public SimpleArray(int size) {
        this.objects = new Object[size];
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void add(T value) {
        this.objects[index++] = value;
    }

    public void set(int index, T model) {
        this.objects[index] = model;
    }

    protected void delete(int ind) {
        for (int i = ind; i < this.objects.length - 1; i++) {
            this.objects[i] = this.objects[i + 1];
        }
        index--;
        this.objects[index] = null;
    }

    public T get(int ind) {
        return (T) this.objects[ind];
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<T>(this.objects);
    }
}
