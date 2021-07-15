  package com.example.qrcontacts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    protected List<Contact> contacts;
    private ContactViewModel contactViewModel;
    CardView clickedContact;
    TextView naamValue;
    TextView emailValue;
    TextView telefoonValue;
    TextView twitterValue;
    TextView facebookValue;
    TextView snapchatValue;
    TextView instagramValue;
    TextView linkedinValue;
    TextView tiktokValue;
    TextView geboortedatumValue;
    Button closeContactButton;
    EditText searchbar;
    ContactAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.contacts_fragment, container, false);
        clickedContact = v.findViewById(R.id.scannedContact);
        clickedContact.setVisibility(View.INVISIBLE);
        naamValue = v.findViewById(R.id.naamValue);
        emailValue = v.findViewById(R.id.publiekeEmailValue);
        telefoonValue = v.findViewById(R.id.telefoonValue);
        twitterValue = v.findViewById(R.id.twitterValue);
        facebookValue = v.findViewById(R.id.facebookValue);
        snapchatValue = v.findViewById(R.id.snapchatValue);
        instagramValue = v.findViewById(R.id.instagramValue);
        linkedinValue = v.findViewById(R.id.linkedinValue);
        tiktokValue = v.findViewById(R.id.tiktokValue);
        geboortedatumValue = v.findViewById(R.id.geboortedatumValue);
        searchbar = v.findViewById(R.id.searchbar);
        searchbar.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterContactList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchbar.setInputType(InputType.TYPE_CLASS_TEXT);
        closeContactButton = v.findViewById(R.id.closeContactButton);
        closeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedContact.setVisibility(View.INVISIBLE);
            }
        });




        RecyclerView recyclerView = v.findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContacts(contacts);
            }
        });

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                naamValue.setText(contact.getName());
                emailValue.setText(contact.getPublic_email());
                telefoonValue.setText(contact.getTelefoonnummer());
                twitterValue.setText(contact.getTwitter());
                facebookValue.setText(contact.getFacebook());
                snapchatValue.setText(contact.getSnapchat());
                instagramValue.setText(contact.getInstagram());
                linkedinValue.setText(contact.getLinkedin());
                tiktokValue.setText(contact.getTiktok());
                geboortedatumValue.setText(contact.getGeboortedatum());
                clickedContact.setVisibility(View.VISIBLE);
                clickedContact.requestFocus();
            }
        });

        return v;
    }


}
