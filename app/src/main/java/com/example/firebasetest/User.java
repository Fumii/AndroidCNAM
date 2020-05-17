package com.example.firebasetest;

public class User {
    private String id;
    private String nom;
    private String latitude;
    private String longitude;

    User(String id, String nom, String latitude, String longitude){
        this.id = id;
        this.nom= nom;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    void setId(String id){
        this.id = id;
    }
    void setNom(String nom){
        this.nom=nom;
    }
    void setLatitude(String latitude){
        this.latitude=latitude;
    }
    void setLongitude(String longitude){

    }

    String getId(){
        return this.id;
    }
    String getNom(){
        return this.nom;
    }
    String getLatitude(){ return this.latitude;}
    String getLongitude(){
        return this.longitude;
    }
}
