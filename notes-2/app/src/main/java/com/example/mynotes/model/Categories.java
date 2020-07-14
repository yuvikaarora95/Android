package com.example.mynotes.model;

import java.io.Serializable;

public class Categories implements Serializable {
    public String name;
    public int id;

    public Categories(String name, int id) {
        this.name = name;
        this.id = id;
    }
    @Override
    public String toString() {
        return name;
    }
}
