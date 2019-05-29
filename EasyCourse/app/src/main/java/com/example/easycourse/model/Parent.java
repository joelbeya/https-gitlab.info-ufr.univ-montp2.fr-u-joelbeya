package com.example.easycourse.model;

import java.util.ArrayList;

public class Parent extends User {
    private ArrayList<Student> children;

    public ArrayList<Student> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Student> children) {
        this.children = children;
    }
}
