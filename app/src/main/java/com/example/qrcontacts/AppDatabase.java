package com.example.qrcontacts;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO contactsDAO();

    private static AppDatabase instance;

    static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "contacts").fallbackToDestructiveMigration().build();
    }
}
