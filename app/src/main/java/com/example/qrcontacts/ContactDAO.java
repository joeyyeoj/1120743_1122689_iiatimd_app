package com.example.qrcontacts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Insert
    void InsertContact(Contact contact);

    @Delete
    void delete(Contact contact);
}
