package com.msbilgin.sqlkolayexample;

public class User {
    public long uid;
    public String name;
    public String surname;
    public int age;
    public double income;

    public User(long uid, String name, String surName, int age, double income) {
        this.uid = uid;
        this.name = name;
        this.surname = surName;
        this.age = age;
        this.income = income;
    }

    /**
     * Calculate something
     *
     * @return
     */
    public double calc() {
        return income / age;
    }

}
