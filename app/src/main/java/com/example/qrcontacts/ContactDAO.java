package com.example.qrcontacts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertContact(Contact contact);

    @Update
    void UpdateContact(Contact contact);

    @Delete
    void DeleteContact(Contact contact);

}
