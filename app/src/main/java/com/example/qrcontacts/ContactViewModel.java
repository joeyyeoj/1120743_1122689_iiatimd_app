package com.example.qrcontacts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactsRepository repo;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repo = new ContactsRepository(application);
        allContacts = repo.getAllContacts();

    }

    public void insert(Contact contact){
        repo.insert(contact);
    }

    public void update(Contact contact){
        repo.update(contact);
    }

    public void delete(Contact contact){
        repo.delete(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }


}
