package com.example.ethical_hacker.myapplication2.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.ethical_hacker.myapplication2.MyDbHelper_E;
import com.example.ethical_hacker.myapplication2.R;
import com.example.ethical_hacker.myapplication2.SessionManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.graphics.Color.rgb;


public class HomePageF extends Fragment {
    View view;

    MyDbHelper_E db;
    PieChart pieChart;
    SessionManager sessionManager;
    private int[] COLORS = {rgb(151, 0, 0), rgb(6, 79, 0)};
    private TextView c_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        pieChart = (PieChart) view.findViewById(R.id.PieChart);
        c_date = (TextView) view.findViewById(R.id.c_date);
        db = new MyDbHelper_E(getActivity());
        sessionManager = new SessionManager(getActivity());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy");
        Date currentDate = new Date();
        String currdate = simpleDateFormat.format(currentDate);
        c_date.setText(currdate);
        getMonthData(currdate);
        c_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField();
            }
        });
        return view;
    }

    private void getMonthData(String currdate) {
        int amt = db.getMonthExpense(currdate, sessionManager.get_User_Id());
        int incAmt = db.getMonthInc(currdate, sessionManager.get_User_Id());
        setChart(amt, incAmt);
    }

    private void setChart(int amt, int incAmt) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(amt, "Expense", 0));
        entries.add(new PieEntry(incAmt, "Income", 1));

        PieDataSet dataset = new PieDataSet(entries, "Monthly Report");
        dataset.setColors(COLORS);
        PieData data = new PieData(dataset);
        dataset.setValueTextSize(18);
        pieChart.setData(data);
        data.setValueTextColor(Color.WHITE);
        pieChart.invalidate();
    }

    private void setDateTimeField() {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                c_date.setText(dateFormatter.format(newDate.getTime()));
                getMonthData(c_date.getText().toString());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 7);
        fromDatePickerDialog.show();
    }


}
