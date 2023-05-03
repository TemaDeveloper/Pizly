package com_pizly.java_pizly.pizly.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "sharedPreferencesUser";
    private static final String KEY_NIGHT = "night_mode_key";
    private static final String KEY_ID = "user_id_key";

    private static SharedPrefManager instance;
    private static Context context;
    private SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public int getKeyID() {
        int id = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getInt(KEY_ID, 0);
        return id;
    }

    //theme setting
    public void setKeyID(int id) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.apply();
    }

    public boolean getMode() {
        Boolean state = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_NIGHT, true);
        return state;
    }

    //theme setting
    public void setMode(boolean isChecked) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_NIGHT, isChecked);
        editor.apply();
    }

}
