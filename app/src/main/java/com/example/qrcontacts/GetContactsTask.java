package com.example.qrcontacts;

public class GetContactsTask implements Runnable {

    AppDatabase db;
    ContactsFragment fragment;

    public GetContactsTask(AppDatabase db, ContactsFragment fragment){
        this.db = db;
        this.fragment = fragment;
    }

    @Override
    public void run(){

    }
}
