package com.lymin.nexonlinemarket.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceX {

    private Context context;
    private SharedPreferences prefs;

    public SharePreferenceX(Context context) {
        this.context = context;
        try {
            prefs = this.context.getSharedPreferences("ConfigSerivce", MODE_PRIVATE);
        }catch (Exception e){
            Log.d("ConfigService", "SharePreferenceTools: "+e.getMessage());
        }
    }
    public String getPrefernces(String st) {
        try {
            String strResult = prefs.getString(st, "Null");
            return strResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("ConfigSerivce", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("ConfigSerivce", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("ConfigSerivce", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
