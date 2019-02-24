package com.example.med_hack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> contactName;
    ArrayList<String> Position;

    public ContactAdapter(Context context1,
                      ArrayList<String> contactName,
                          ArrayList<String> Position) {
        this.context = context1;
        this.contactName = contactName;
        this.Position = Position;
    }

    public int getCount() {
        return contactName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;
        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.contacts, null);

            holder = new Holder();
            holder.txtcontactname = (TextView) child.findViewById(R.id.txtcontactname);
            holder.txtposition = (TextView) child.findViewById(R.id.txtposition);

            child.setTag(holder);

        } else {
            holder = (Holder) child.getTag();
        }

        holder.txtcontactname.setText(contactName.get(position));
        holder.txtposition.setText(Position.get(position));

        return child;
    }

    public class Holder {
        TextView txtcontactname,txtposition;
    }
}



