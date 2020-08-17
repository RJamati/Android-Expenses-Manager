package com.example.ethical_hacker.myapplication2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ethical_hacker.myapplication2.HttpProcessor;
import com.example.ethical_hacker.myapplication2.Model;
import com.example.ethical_hacker.myapplication2.MyDbHelper_E;
import com.example.ethical_hacker.myapplication2.R;
import com.example.ethical_hacker.myapplication2.SessionManager;
import com.example.ethical_hacker.myapplication2.UpdateExpense;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddCategoryF extends Fragment implements View.OnClickListener{
    //, HttpProcessor.HttpResponser
    EditText catname;
    Button catsubmit;
    ListView catlist;
    MyDbHelper_E db;
    Context context;
    SessionManager sessionManager;
    ArrayList<Model> arrayListModel;
    View view;

    Toolbar toolbar;
    String id1, cat_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.content_add_category, container, false);

        context = getActivity();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        sessionManager = new SessionManager(context);
        catname = (EditText) view.findViewById(R.id.categoryname);
        catsubmit = (Button) view.findViewById(R.id.cat_submit);
        catlist = (ListView) view.findViewById(R.id.cat_list);
        catsubmit.setOnClickListener(this);
        db = new MyDbHelper_E(context);
        catlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialogLogOut(i, "Do you want to remove this item from your list?", "Delete");
                return true;
            }
        });

        catlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialogLogOut(i, "Do you want to update the data?", "Update");
            }
        });
        getCategory();
        //updatePersonalInfo();
        return view;
    }

    private void AlertDialogLogOut(final int position, String msg, final String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (title.equals("Delete")) {
                    db.deleteData(arrayListModel.get(position).getId(), MyDbHelper_E.Category_Table);
                    getCategory();
                } else {
                    final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Update Category");
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment newFragment = new AddCategoryF();
                            // consider using Java coding conventions (upper first char class names!!!)
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();

                            // Replace whatever is in the fragment_container view with this fragment,
                            // and add the transaction to the back stack
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);

                            // Commit the transaction
                            transaction.commit();
                            draw_UI();
                        }
                    });
                    id1 = arrayListModel.get(position).getId();
                    cat_name = arrayListModel.get(position).getCategoryname();
                    catname.setText(cat_name);
                    catsubmit.setText("Update");
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
                    getCategory();
                }
            }
        }
    }

    private void getCategory() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayListModel = db.getCategoryData(sessionManager.get_User_Id());
        for (int i = 0; i < arrayListModel.size(); i++) {
            arrayList.add(arrayListModel.get(i).getCategoryname());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, arrayList);
        catlist.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cat_submit) {
            if ((!catname.getText().toString().equals("")) && catsubmit.getText().equals("Submit")) {
                boolean inserted = db.setCategory(sessionManager.get_User_Id(), catname.getText().toString());
                if (inserted) {
                    getCategory();
                    Toast.makeText(context, "Category Added", Toast.LENGTH_LONG).show();
                    catname.setText("");
                }
            }

            else if(catsubmit.getText().equals("Update") && (!catname.getText().toString().equals(""))){

                boolean inserted = db.updateCategory("Category", id1,catname.getText().toString());
                if (inserted) {
                    Toast.makeText(context, "Record Updated", Toast.LENGTH_LONG).show();

                    catname.setText("");
                    catsubmit.setText("Submit");
                    getCategory();
                    draw_UI();
                }
            }
            else {
                Toast.makeText(context, "Enter Category Name", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void draw_UI(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Category");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

//    private void updatePersonalInfo() {
//        //  RequestBody requestBody = RequestBody.create(API.JSON, jsonObject.toString());
//        RequestBody requestBody = new FormEncodingBuilder()
//                //   .add("access_token", sessionManager.getAccessToken())
//                .build();
//        HttpProcessor httpProcessor = new HttpProcessor(context, true, "https://httpbin.org/get", HttpProcessor.GET, requestBody);
//        httpProcessor.executeRequest("GET");
//        httpProcessor.setHttpResponserListener(this);
//    }
//
//    @Override
//    public void responseResult(String result, String TAG) {
//        if (TAG.equals("GET")) {
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                JSONObject jsonObject1 = jsonObject.optJSONObject("headers");
//                String host = jsonObject1.optString("Host");
//                Toast.makeText(context, host, Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//
//            }
//        }
//   }

}
