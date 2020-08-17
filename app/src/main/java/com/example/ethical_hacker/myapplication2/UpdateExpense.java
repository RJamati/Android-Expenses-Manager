package com.example.ethical_hacker.myapplication2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpdateExpense extends AppCompatActivity {

    Context context;
    Intent intent;
    TextView update_title;
    EditText amt, date1, comment;
    Spinner spinner;
    Button update;
    MyDbHelper_E db;
    SessionManager sessionManager;
    private String id;
    private String table;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);
        intent = new Intent();
        context = UpdateExpense.this;
        sessionManager = new SessionManager(context);
        db = new MyDbHelper_E(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        toolbar.setBackgroundColor(colorCode);
        //  setStatusBarColor(ContextCompat.getColor(this, R.color.color_green_primary));
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));

        update_title = (TextView) findViewById(R.id.upd_expense_title);
        amt = (EditText) findViewById(R.id.upd_amt);
        date1 = (EditText) findViewById(R.id.upd_date1);
        comment = (EditText) findViewById(R.id.upd_comment);
        spinner = (Spinner) findViewById(R.id.upd_spinner);
        update = (Button) findViewById(R.id.upd_submit);
        getCategory();
        intent = getIntent();
        id = intent.getStringExtra("id");
        table = intent.getStringExtra("type");
        amt.setText(intent.getStringExtra("amt"));
        date1.setText(intent.getStringExtra("date1"));
        comment.setText(intent.getStringExtra("cmt1"));
        update_title.setText(intent.getStringExtra("page_title"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inserted = db.update(table, id, spinner.getSelectedItem().toString(), Integer.valueOf(amt.getText().toString()), comment.getText().toString(), date1.getText().toString());
                if (inserted) {
                    Toast.makeText(context, "Record Updated", Toast.LENGTH_LONG).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setDateTimeField();

            }
        });
    }

    private void getCategory() {

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<Model> arrayListModel = db.getCategoryData(sessionManager.get_User_Id());
        for (int i = 0; i < arrayListModel.size(); i++) {
            arrayList.add(arrayListModel.get(i).getCategoryname());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, arrayList);
        spinner.setAdapter(adapter);

    }


    private void setDateTimeField() {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date1.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 7);
        fromDatePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
