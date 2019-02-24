package com.example.med_hack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StatusComAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> name;
    ArrayList<String> status;
    ArrayList<String> datetime;
    ArrayList<String> mood;

    public StatusComAdapter(Context context1,
                       ArrayList<String> name,
                       ArrayList<String> status,
                       ArrayList<String> datetime,
                       ArrayList<String> mood) {
        this.context = context1;
        this.name = name;
        this.status = status;
        this.datetime = datetime;
        this.mood = mood;
    }

    public int getCount() {
        return name.size();
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
            child = layoutInflater.inflate(R.layout.feed, null);

            holder = new Holder();
            holder.txtname = (TextView) child.findViewById(R.id.txtname);
            holder.txtstatus = (TextView) child.findViewById(R.id.txtstatus);
            holder.txtdatetime = (TextView) child.findViewById(R.id.txtdatetime);

            child.setTag(holder);

        } else {
            holder = (Holder) child.getTag();
        }

        holder.txtname.setText(name.get(position));
        holder.txtstatus.setText(status.get(position));
        holder.txtdatetime.setText(datetime.get(position));

        return child;
    }

    public class Holder {
        TextView txtname;
        TextView txtstatus;
        TextView txtdatetime;
        ImageView mood;
    }
}