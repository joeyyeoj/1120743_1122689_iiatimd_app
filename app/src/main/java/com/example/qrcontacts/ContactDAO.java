package com.example.qrcontacts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contacts ORDER BY naam ASC")
    LiveData<List<Contact>> getAllContacts();

    @Insert
    void InsertContact(Contact contact);

    @Update
    void UpdateContact(Contact contact);

    @Delete
    void DeleteContact(Contact contact);
}