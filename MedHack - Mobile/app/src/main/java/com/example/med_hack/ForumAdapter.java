package com.example.med_hack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ForumAdapter  extends BaseAdapter {

    Context context;
    ArrayList<String> ann;
    ArrayList<String> moodtype;
    ArrayList<String> vote;

    public ForumAdapter(Context context1,
                          ArrayList<String> ann,
                          ArrayList<String> moodtype,
                        ArrayList<String> vote) {
        this.context = context1;
        this.ann = ann;
        this.moodtype = moodtype;
        this.vote = vote;
    }

    public int getCount() {
        return ann.size();
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
            child = layoutInflater.inflate(R.layout.forum, null);

            holder = new Holder();
            holder.txtann = (TextView) child.findViewById(R.id.txtann);
            holder.txtmoodtype = (TextView) child.findViewById(R.id.txtmoodtype);
            holder.txtvote = (TextView) child.findViewById(R.id.txtvote);

            child.setTag(holder);

        } else {
            holder = (Holder) child.getTag();
        }

        String getv = "";
        try {
            String[] v = vote.get(position).split(",");
            getv = String.valueOf(v.length);
        }
        catch (Exception e){
            getv = ""+0;
        }
        holder.txtann.setText(ann.get(position));
        holder.txtmoodtype.setText("Feeling "+moodtype.get(position)+"?");
        holder.txtvote.setText(getv);

        return child;
    }

    public class Holder {
        TextView txtann,txtmoodtype,txtvote;
    }
}




