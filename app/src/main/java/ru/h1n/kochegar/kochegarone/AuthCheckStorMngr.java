package ru.h1n.kochegar.kochegarone;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Павел on 05.10.2017.
 */

public class AuthCheckStorMngr  {

    private final SharedPreferences preferences;
    private boolean authCheckStatus;  //текущее состояние авторизации
    public static final String APP_PREFERENCES = "kochegarSetting";
    public static final String APP_AUTH_STATUS = "statusAuth";

    public AuthCheckStorMngr(Context context) {
         preferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        authCheckStatus = false;
    }
    public void setAuth() {
        /**
         * SharedPreference для хранения состояния залогирован
         *public void setAuth(String userName)
         */

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(APP_AUTH_STATUS, true);

        editor.apply();
    }

    public boolean checkAuth() {

        try {

            if (preferences.contains(APP_AUTH_STATUS)) {
                authCheckStatus = preferences.getBoolean(APP_AUTH_STATUS, false);
            }

        } catch (ClassCastException e) {
                      return authCheckStatus;
        }
        catch (NullPointerException npe) {
                       return authCheckStatus;
        }
        return authCheckStatus;
    }





}
