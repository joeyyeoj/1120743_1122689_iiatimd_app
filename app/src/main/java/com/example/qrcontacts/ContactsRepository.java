package com.example.qrcontacts;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactsRepository {
    private ContactDAO contactDAO;
    private LiveData<List<Contact>> allContacts;

    public ContactsRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        contactDAO = database.contactsDAO();
        allContacts = contactDAO.getAllContacts();
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDAO).execute(contact);
    }

    public void update(Contact contact){
        new UpdateContactAsyncTask(contactDAO).execute(contact);
    }

    public void delete(Contact contact){
        new DeleteContactAsyncTask(contactDAO).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts(){
        return allContacts;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDAO contactDao;

        private InsertContactAsyncTask(ContactDAO contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.InsertContact(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDAO contactDao;

        private UpdateContactAsyncTask(ContactDAO contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.UpdateContact(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDAO contactDao;

        private DeleteContactAsyncTask(ContactDAO contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.DeleteContact(contacts[0]);
            return null;
        }
    }
}
