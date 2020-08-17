package com.example.ethical_hacker.myapplication2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {

    private static final String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";

    private static final String USER_ID="User_Id";
    private static final String NAME="User_name";
    private static final String MOBILE="User_mobile";

    private Context context;

    private SharedPreferences pref;

    private Editor editor;

    private String PREFERENCE_NAME = "expense";

    public SessionManager(Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;
        pref = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }
    public void saveLoggedIn(boolean status) {

        editor.putBoolean(KEY_IS_LOGGED_IN, status);
        editor.commit();
    }

    public void set_User_Id(String id, String username, String mobile) {

        editor.putString(USER_ID, id);
        editor.putString(MOBILE, mobile);
        editor.putString(NAME, username);
        editor.commit();
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String get_User_Id() {

        return pref.getString(USER_ID,"");
    }
 public String get_User_Name() {

        return pref.getString(NAME,"");
    }
 public String get_User_Mobile() {

        return pref.getString(MOBILE,"");
    }

    public void logout() {
        editor.clear();
        editor.commit();
        //    context.startActivity(new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        ((Activity)context).finish();
    }

}