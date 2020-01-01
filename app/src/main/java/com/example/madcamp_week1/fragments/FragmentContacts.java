package com.example.madcamp_week1.fragments;

import androidx.fragment.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcamp_week1.R;
import com.example.madcamp_week1.adapters.ContactsRvAdapter;
import com.example.madcamp_week1.models.ModelContacts;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment{

    private View v;
    private RecyclerView recyclerView;
    private ContactsRvAdapter mAdapter;
    public FragmentContacts(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.frag_contacts, container, false);

        recyclerView = v.findViewById(R.id.rv_contacts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.LayoutManager layoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(layoutManager);

        ContactsRvAdapter adapter = new ContactsRvAdapter(getContext(),getContacts());

        recyclerView.setAdapter(adapter);

        return v;
    }


    private List<ModelContacts> getContacts() {

        List<ModelContacts> list = new ArrayList<>();
        Cursor cursor = null;
        cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();


        do {
            list.add(new ModelContacts(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI))
            ));
        } while (cursor.moveToNext());
        return list;
    }
}