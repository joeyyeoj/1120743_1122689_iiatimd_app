package com.example.qrcontacts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    private List<Contact> contacts;
    private ContactViewModel contactViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.contacts_fragment, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                Log.d("loggie", "hoerending");
                adapter.setContacts(contacts);
            }
        });

        return v;
    }

}
