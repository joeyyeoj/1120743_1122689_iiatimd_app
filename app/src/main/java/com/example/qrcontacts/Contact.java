package com.example.qrcontacts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    @ColumnInfo
    private String naam;

    @ColumnInfo(defaultValue = "Geen email-adres")
    private String publieke_email;

    @ColumnInfo(defaultValue = "Geen telefoonnummer")
    private String telefoonnummer;

    @ColumnInfo(defaultValue = "Geen Twitter")
    private String twitter;

    @ColumnInfo(defaultValue = "Geen Facebook")
    private String facebook;

    @ColumnInfo(defaultValue = "Geen Snapchat")
    private String snapchat;

    @ColumnInfo(defaultValue = "Geen Instagram")
    private String instagram;

    @ColumnInfo(defaultValue = "Geen LinkedIn")
    private String linkedin;

    @ColumnInfo(defaultValue = "Geen TikTok")
    private String tiktok;

    @ColumnInfo(defaultValue = "Geen geboortedatum")
    private String geboortedatum;

    @ColumnInfo(defaultValue = "Geen adres")
    private String adres;

    @ColumnInfo(defaultValue = "Geen woonplaats")
    private String woonplaats;

    @ColumnInfo(defaultValue = "Geen postcode")
    private String postcode;

    @ColumnInfo(defaultValue = "Geen land")
    private String land;


    public Contact(int uuid, String naam, String publieke_email, String telefoonnummer,
                   String twitter, String facebook, String snapchat, String instagram,
                   String linkedin, String tiktok, String geboortedatum, String adres, String woonplaats,
                   String postcode, String land){
        this.uuid = uuid;
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

    public int getUuid(){
        return this.uuid;
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
