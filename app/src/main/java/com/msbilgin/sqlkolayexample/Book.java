package com.msbilgin.sqlkolayexample;

import java.util.Date;

public class Book {
    public int id;
    public String name;
    public String author;
    public String publishDate;
    public double price;

    public Book() {
    }

    public Book(String name, String author, String publishDate, double price) {
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.price = price;
    }
}
