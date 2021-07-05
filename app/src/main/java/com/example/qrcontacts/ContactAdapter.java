package com.example.qrcontacts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    protected List<Contact> contacts = new ArrayList<>();
    private OnItemClickListener listener;
    protected List<Contact> untouchedContacts = new ArrayList<>();


    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactcard, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        holder.textViewNaam.setText(currentContact.getNaam());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
        this.untouchedContacts = contacts;
        notifyDataSetChanged();
    }

    class ContactHolder extends RecyclerView.ViewHolder{
        private TextView textViewNaam;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            textViewNaam = itemView.findViewById(R.id.textviewName);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(contacts.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void filterContactList(String filterText){
        List<Contact> filteredContacts = new ArrayList<>();

        if(filterText != null){
            for(Contact contact : this.untouchedContacts){
                if(contact.getNaam().toLowerCase().startsWith(filterText.toLowerCase())){
                    filteredContacts.add(contact);
                }
            }
            this.contacts = filteredContacts;
            notifyDataSetChanged();
        }
        else{
            this.contacts = untouchedContacts;
        }

    }
}
