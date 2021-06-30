package com.example.qrcontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) { this.contacts = contacts; }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView textview;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.contactcard, parent, false);
        ContactViewHolder villagerViewHolder = new ContactViewHolder(v);
        return villagerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.textview.setText(contacts.get(position).getNaam());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
