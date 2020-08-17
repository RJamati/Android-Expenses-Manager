package com.example.ethical_hacker.myapplication2.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ethical_hacker.myapplication2.Model;
import com.example.ethical_hacker.myapplication2.MyDbHelper_E;
import com.example.ethical_hacker.myapplication2.R;
import com.example.ethical_hacker.myapplication2.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddIncome extends Fragment implements View.OnClickListener {
    View view;
    EditText edittext, amount, comment;
    Button expsubmit;
    Spinner categoryspinner;
    TextView expense_title;
    MyDbHelper_E db;
    Context context;
    SessionManager sessionManager;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ArrayList<Model> arrayListModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.content_expense, container, false);


        context = getActivity();


        edittext = (EditText) view.findViewById(R.id.date1);

        amount = (EditText) view.findViewById(R.id.amt);
        comment = (EditText) view.findViewById(R.id.comment);
        expsubmit = (Button) view.findViewById(R.id.exp_submit);
        categoryspinner = (Spinner) view.findViewById(R.id.spinner);
        expsubmit.setOnClickListener(this);
        db = new MyDbHelper_E(context);
        sessionManager = new SessionManager(context);
        getCategory();
        expense_title = (TextView) view.findViewById(R.id.expense_title);
        expense_title.setText("Income");

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setDateTimeField();

            }
        });
        return view;
    }

    private void setDateTimeField() {
        //date.setOnClickListener(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edittext.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 7);
        fromDatePickerDialog.show();
    }

    private void getCategory() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayListModel = db.getCategoryData(sessionManager.get_User_Id());
        for (int i = 0; i < arrayListModel.size(); i++) {
            arrayList.add(arrayListModel.get(i).getCategoryname());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, arrayList);
        categoryspinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.exp_submit) {
            boolean inserted = db.setIncome(sessionManager.get_User_Id(), categoryspinner.getSelectedItem().toString(), Integer.valueOf(amount.getText().toString()), comment.getText().toString(), edittext.getText().toString());
            if (inserted) {
                Toast.makeText(getActivity(), "Income has been added", Toast.LENGTH_LONG).show();
                clearText();
            }
        }
    }


    private void clearText() {
        amount.setText("");
        comment.setText("");
        edittext.setText("");
    }
}
