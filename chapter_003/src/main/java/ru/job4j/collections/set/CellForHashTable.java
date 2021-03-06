package ru.job4j.collections.set;

public class CellForHashTable<T> {
    protected int hash;
    protected T data;

    public CellForHashTable(int hash, T data) {
        this.hash = hash;
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

}
