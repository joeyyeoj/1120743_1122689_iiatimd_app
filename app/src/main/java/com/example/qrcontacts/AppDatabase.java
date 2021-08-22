package com.example.qrcontacts;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, version = 123468)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO contactsDAO();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "contact_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDAO contactDAO;

        private populateDbAsyncTask(AppDatabase db){
            contactDAO = db.contactsDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            contactDAO.InsertContact(new Contact( "Joey 1", "test@test.com", "42069", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test"));
//            contactDAO.InsertContact(new Contact("Joey 3", "", "", "", "", "", "", "", "", "", "", "", "", ""));
//            contactDAO.InsertContact(new Contact("Joey 2", "", "", "", "", "", "", "", "", "", "", "", "", ""));
            return null;
        }
    }

}
