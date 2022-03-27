package com.lydia.prenly.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class MyPreferences {
    int PRIVATE_MODE = 0;
    private String TAG = MyPreferences.class.getSimpleName();

    SharedPreferences pref;

    SharedPreferences.Editor editor;


    Context _context;

    private static final String PREF_NAME = "bantu_pref";
    private static final String SELECTED_URL = "selected_url";
    private static final String SELECTED_TOPIC = "selected_topic";

    public MyPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setSelectedUrl(String accountID) {
        editor.putString(SELECTED_URL, accountID);
        editor.commit();

    }

    public String getSelectedUrl() {

        if (pref.getString(SELECTED_URL, null) != null)
            return pref.getString(SELECTED_URL, null);
        else
            return null;
    }

    public void setSelectedTopic(String selectedTopic) {
        editor.putString(SELECTED_TOPIC, selectedTopic);
        editor.commit();

    }

    public String getSelectedTopic() {

        if (pref.getString(SELECTED_TOPIC, null) != null)
            return pref.getString(SELECTED_TOPIC, null);
        else
            return null;
    }
}