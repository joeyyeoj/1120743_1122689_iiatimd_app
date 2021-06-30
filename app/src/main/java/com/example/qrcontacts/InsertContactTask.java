package com.example.qrcontacts;



public class InsertContactTask implements Runnable {
    AppDatabase db;
    Contact contact;

    public InsertContactTask(AppDatabase db, Contact contact){
        this.db = db;
        this.contact = contact;
    }
    @Override
    public void run(){
        db.contactsDAO().InsertContact(this.contact);
    }
}
