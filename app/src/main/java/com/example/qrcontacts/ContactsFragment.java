package com.example.qrcontacts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ContactsFragment cf = this;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.contacts_fragment, container, false);
        AppDatabase db = AppDatabase.getInstance(getActivity());
        recyclerView = v.findViewById(R.id.contactRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                new GetContactsTask(db, cf).run();
            }
        });
        return v;
    }

    public void setContacts(List<Contact> contacts){
        ContactAdapter recyclerViewAdapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
