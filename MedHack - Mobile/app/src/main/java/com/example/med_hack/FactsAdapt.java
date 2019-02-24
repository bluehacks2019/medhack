package com.example.med_hack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FactsAdapt extends BaseAdapter {

    Context context;
    ArrayList<String> Description;

    public FactsAdapt(Context context1,
                       ArrayList<String> Description) {
        this.context = context1;
        this.Description = Description;
    }

    public int getCount() {
        return Description.size();
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
            child = layoutInflater.inflate(R.layout.facts, null);

            holder = new Holder();
            holder.txtfacts = (TextView) child.findViewById(R.id.txtfacts);

            child.setTag(holder);

        } else {
            holder = (Holder) child.getTag();
        }

        holder.txtfacts.setText(Description.get(position));

        return child;
    }

    public class Holder {
        TextView txtfacts;
    }
}

