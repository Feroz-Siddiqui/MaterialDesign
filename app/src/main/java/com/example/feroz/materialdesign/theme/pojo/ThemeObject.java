package com.example.feroz.materialdesign.theme.pojo;


public class ThemeObject {

    private String color;
    private String hashcode;

    public ThemeObject() {
    }

    public ThemeObject(String color, String hashcode) {
        this.color = color;
        this.hashcode = hashcode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
