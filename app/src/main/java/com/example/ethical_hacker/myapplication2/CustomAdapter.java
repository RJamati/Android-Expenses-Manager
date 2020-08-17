package com.example.ethical_hacker.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 12/21/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Model> arrayList;


    public CustomAdapter(Context context, ArrayList<Model> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.customadapter, parent, false);
        TextView catname = (TextView) rowView.findViewById(R.id.catnameValue);
        TextView catdate = (TextView) rowView.findViewById(R.id.catdate);
        TextView catcomment = (TextView) rowView.findViewById(R.id.catcommentValue);
        TextView catamount = (TextView) rowView.findViewById(R.id.catamountValue);

        catname.setText(arrayList.get(position).getCategoryname());
        catdate.setText(arrayList.get(position).getDate());
        catcomment.setText(arrayList.get(position).getComment());
        catamount.setText(arrayList.get(position).getAmount());

        return rowView;
    }



}
