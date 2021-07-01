package com.example.qrcontacts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "contacts")
public class Contact {

    public Contact(String naam, String publieke_email, String telefoonnummer, String twitter, String facebook, String snapchat, String instagram, String linkedin, String tiktok, String geboortedatum, String adres, String woonplaats, String postcode, String land){
        this.naam = naam;
        this.publieke_email = publieke_email;
        this.telefoonnummer = telefoonnummer;
        this.twitter = twitter;
        this.facebook = facebook;
        this.snapchat = snapchat;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.tiktok = tiktok;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
        this.woonplaats = woonplaats;
        this.postcode = postcode;
        this.land = land;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    protected int id;

    @ColumnInfo(name = "naam")
    private String naam;

    @ColumnInfo(name = "publieke_email")
    private String publieke_email;

    @ColumnInfo(name = "telefoonnummer")
    private String telefoonnummer;

    @ColumnInfo(name = "twitter")
    private String twitter;

    @ColumnInfo(name = "facebook")
    private String facebook;

    @ColumnInfo(name = "snapchat")
    private String snapchat;

    @ColumnInfo(name = "instagram")
    private String instagram;

    @ColumnInfo(name = "linkedin")
    private String linkedin;

    @ColumnInfo(name = "tiktok")
    private String tiktok;

    @ColumnInfo(name = "geboortedatum")
    private String geboortedatum;

    @ColumnInfo(name = "adres")
    private String adres;

    @ColumnInfo(name = "woonplaats")
    private String woonplaats;

    @ColumnInfo(name = "postcode")
    private String postcode;

    @ColumnInfo(name = "land")
    private String land;


    public int getUuid(){
        return this.id;
    }

    public String getNaam() {
        return this.naam;
    }

    public String getPublieke_email() {
        return this.publieke_email;
    }

    public String getTelefoonnummer(){
        return this.telefoonnummer;
    }

    public String getTwitter(){
        return this.twitter;
    }

    public String getFacebook(){
        return this.facebook;
    }

    public String getSnapchat(){
        return this.snapchat;
    }

    public String getInstagram(){
        return this.instagram;
    }

    public String getLinkedin(){
        return this.linkedin;
    }

    public String getTiktok(){
        return this.tiktok;
    }

    public String getGeboortedatum(){
        return this.geboortedatum;
    }

    public String getAdres(){
        return this.adres;
    }

    public String getWoonplaats(){
        return this.woonplaats;
    }

    public String getPostcode(){
        return this.postcode;
    }

    public String getLand(){
        return this.land;
    }
}
