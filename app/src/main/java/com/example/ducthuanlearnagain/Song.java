package com.example.ducthuanlearnagain;

public class Song {
    private String name;
    private int file = 0;

    public Song(String name, int file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public int getFile() {
        return file;
    }
}
