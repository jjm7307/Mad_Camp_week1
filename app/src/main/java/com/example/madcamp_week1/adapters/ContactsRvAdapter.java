package com.example.madcamp_week1.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcamp_week1.R;
import com.example.madcamp_week1.models.ModelContacts;

import java.io.File;
import java.util.List;

public class ContactsRvAdapter extends RecyclerView.Adapter<ContactsRvAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ModelContacts> mListContacts;

    public ContactsRvAdapter(Context context, List<ModelContacts> listContacts) {
        mListContacts = listContacts;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.items_contacts, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView contact_name, contact_number;
        ImageView contact_profile;

        contact_name = holder.contact_name;
        contact_number = holder.contact_number;
        contact_profile = holder.contact_profile;

        if (mListContacts.get(position).getPhoto() != null){
            contact_profile.setImageURI(Uri.parse(mListContacts.get(position).getPhoto()));
        }
        else{
            contact_profile.setImageResource(R.drawable.icon_profile_red);
        }
        contact_name.setText(mListContacts.get(position).getName());
        contact_number.setText(mListContacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contact_name, contact_number;
        ImageView contact_call, contact_profile;

        public ViewHolder(View itemView) {
            super(itemView);

            contact_profile = itemView.findViewById(R.id.profile);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_call = itemView.findViewById(R.id.calling);


            contact_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+mListContacts.get(position).getNumber()));
                        mContext.startActivity(intent);
                    }
                }
            });

            contact_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+mListContacts.get(position).getNumber()));
                        mContext.startActivity(intent);
                    }
                }
            });


            /*contact_call.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int position = getAdapterPosition();
                    switch (v.getId()){
                        case R.id.calling:
                            float startX = 0;
                            float startY = 0;
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    startX = event.getX();
                                    startY = event.getY();
                                    contact_call.setColorFilter(0xaa111111, PorterDuff.Mode.DST_IN);
                                    contact_call.setBackgroundResource(R.drawable.shape_cicle_dark);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    float endX = event.getX();
                                    float endY = event.getY();
                                    contact_call.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                                    contact_call.setBackgroundResource(R.drawable.shape_circle);
                                    if (isAClick(startX, endX, startY, endY)) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:" + mListContacts.get(position).getNumber()));
                                        mContext.startActivity(intent);
                                    }
                                    break;
                            }
                            break;
                    }
                    return true;
                }
            });*/



        }
    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > 100/* =5 */ || differenceY > 100);
    }


}