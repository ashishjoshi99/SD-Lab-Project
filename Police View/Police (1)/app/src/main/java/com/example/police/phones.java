package com.example.police;

public class phones {
    private String Brand;
    private String Model;
    private String Date;
    private String Color;
    private String IMEI;

    private phones() {
    }

    private phones(String Brand, String Model, String Date, String Color, String IMEI) {
        Brand = this.Brand;
        Model = this.Model;
        Date = this.Date;
        Color = this.Color;
        IMEI = this.IMEI;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }


}
