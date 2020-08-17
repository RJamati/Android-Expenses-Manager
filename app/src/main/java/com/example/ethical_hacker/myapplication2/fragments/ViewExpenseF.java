package com.example.ethical_hacker.myapplication2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ethical_hacker.myapplication2.CustomAdapter;
import com.example.ethical_hacker.myapplication2.Model;
import com.example.ethical_hacker.myapplication2.MyDbHelper_E;
import com.example.ethical_hacker.myapplication2.R;
import com.example.ethical_hacker.myapplication2.SessionManager;
import com.example.ethical_hacker.myapplication2.UpdateExpense;

import java.util.ArrayList;

public class ViewExpenseF extends Fragment {
    CustomAdapter customAdapter;
    MyDbHelper_E myDbHelper_e;
    private Context context;
    ArrayList<Model> arrayList;
    ListView listView;
    SessionManager sessionManager;
    View view;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.content_view_expense, container, false);

        context = getActivity();
        sessionManager = new SessionManager(context);
        listView = (ListView) view.findViewById(R.id.view_exp_list);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        myDbHelper_e = new MyDbHelper_E(context);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialogLogOut(i, "Do you want to remove this item from your list?", "Delete");
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialogLogOut(i, "Do you want to update the data?", "Update");
            }
        });
        getData();
        return view;


    }

    private void getData() {
        arrayList = myDbHelper_e.getExpenseData(sessionManager.get_User_Id());
        customAdapter = new CustomAdapter(context, arrayList);
        listView.setAdapter(customAdapter);
        if (arrayList.size() > 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }


    private void AlertDialogLogOut(final int position, String msg, final String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (title.equals("Delete")) {
                    myDbHelper_e.deleteData(arrayList.get(position).getId(), MyDbHelper_E.Exp_Table);
                    getData();
                } else {
                    String id = arrayList.get(position).getId();
                    String amt1 = arrayList.get(position).getAmount();
                    String cat_name = arrayList.get(position).getCategoryname();
                    String date1 = arrayList.get(position).getDate();
                    String cmt1 = arrayList.get(position).getComment();
                    Intent intent = new Intent(context, UpdateExpense.class);
                    intent.putExtra("id", id);
                    intent.putExtra("amt", amt1);
                    intent.putExtra("catname", cat_name);
                    intent.putExtra("date1", date1);
                    intent.putExtra("cmt1", cmt1);
                    intent.putExtra("type", MyDbHelper_E.Exp_Table);
                    intent.putExtra("page_title","Update Expense");
                    startActivityForResult(intent, 1);
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == 1) {
                    getData();
                }
            }
        }
    }

}


