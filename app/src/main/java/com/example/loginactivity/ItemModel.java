package com.example.loginactivity;

public class ItemModel {
    private int image;
    private String nama, usia, kota, urla;

    public ItemModel() {

    }

    public ItemModel(int image, String nama, String usia, String kota, String urla){
        this.image = image;
        this.nama = nama;
        this.usia = usia;
        this.kota = kota;
        this.urla = urla;
    }

    public int getImage(){
        return image;
    }

    public String getNama(){
        return nama;
    }

    public String getUsia(){
        return usia;
    }

    public String getKota(){
        return kota;
    }

    public String getUrla(){
        return urla;
    }
}
