package com.example.qrcontacts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "contacts", indices = {@Index(value = {"databaseId"}, unique = true)})
public class Contact {

    public Contact(int databaseId, String name, String public_email, String telefoonnummer, String twitter, String facebook, String snapchat, String instagram, String linkedin, String tiktok, String geboortedatum){
        this.databaseId = databaseId;
        this.name = name;
        this.public_email = public_email;
        this.telefoonnummer = telefoonnummer;
        this.twitter = twitter;
        this.facebook = facebook;
        this.snapchat = snapchat;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.tiktok = tiktok;
        this.geboortedatum = geboortedatum;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    protected int id;

    @ColumnInfo(name = "databaseId")

    protected int databaseId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "public_email")
    private String public_email;

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



    public int getUuid(){
        return this.id;
    }

    public int getDatabaseId(){ return this.databaseId; }

    public String getName() {
        return this.name;
    }

    public String getPublic_email() {
        return this.public_email;
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

}
