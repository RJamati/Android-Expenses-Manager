package com.example.ethical_hacker.myapplication2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ethical_hacker.myapplication2.fragments.AddCategoryF;
import com.example.ethical_hacker.myapplication2.fragments.AddExpense;
import com.example.ethical_hacker.myapplication2.fragments.AddIncome;
import com.example.ethical_hacker.myapplication2.fragments.HomePageF;
import com.example.ethical_hacker.myapplication2.fragments.ViewExpenseF;
import com.example.ethical_hacker.myapplication2.fragments.ViewIncomeF;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    private Fragment[] fragments;
    private String[] fragmentsTAG;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    SessionManager sessionManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = HomePage.this;
        sessionManager = new SessionManager(context);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView textTitle = header.findViewById(R.id.nav_header);
        TextView textMobile = header.findViewById(R.id.textView);
        textTitle.setText(sessionManager.get_User_Name());
        textMobile.setText(sessionManager.get_User_Mobile());
        initFragment(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            initFragment(0);
        } else if (id == R.id.nav_addexpense) {
            initFragment(3);
        } else if (id == R.id.nav_addincome) {
            initFragment(4);
        } else if (id == R.id.nav_addcategory) {
            initFragment(2);
        } else if (id == R.id.nav_viewexpense) {
            initFragment(1);
        } else if (id == R.id.nav_viewincome) {
            initFragment(5);
        } else if (id == R.id.nav_logout) {
            AlertDialogLogOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment(int a) {
        AddCategoryF addcategory = new AddCategoryF();
        HomePageF homePageF = new HomePageF();
        AddExpense addexpense = new AddExpense();
        AddIncome addincome = new AddIncome();
        ViewExpenseF viewexpense = new ViewExpenseF();
        ViewIncomeF viewincome = new ViewIncomeF();
        fragments = new Fragment[]{homePageF, viewexpense, addcategory, addexpense, addincome, viewincome};
        fragmentsTAG = new String[]{"Home", "View Expense", "Add Category", "Add Expense", "Add Income", "View Income"};

        changeFragment(a);

    }

    public void changeFragment(int pos) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragments[pos], fragmentsTAG[pos]);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(fragmentsTAG[pos]);
    }

    private void AlertDialogLogOut() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        //  alertDialogBuilder.setTitle("Alert !!!");
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(context, Login.class);
                startActivity(intent);
                SessionManager sessionManager = new SessionManager(context);
                sessionManager.logout();
                finish();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
