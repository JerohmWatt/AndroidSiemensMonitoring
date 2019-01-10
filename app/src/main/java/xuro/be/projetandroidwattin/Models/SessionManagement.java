package xuro.be.projetandroidwattin.Models;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import xuro.be.projetandroidwattin.Activity.LoginActivity;

public class SessionManagement {
        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "WattinDroidPref";

        // All Shared Preferences Keys
        private static final String IS_LOGIN = "IsLoggedIn";

        // Email address (make variable public to access from outside)
        public static final String KEY_EMAIL = "email";

    public static final String KEY_RIGHTS = "rights";

        //tag log
        private static final String TAG = "SESSION";

        // Constructor
        public SessionManagement(Context context){
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        //store login email in shared prefs
        public void createLoginSession(String email, String rights){
            Log.v("sessionmanager","sessmanager" + email);
            editor.putString(KEY_EMAIL,email);
            editor.putString(KEY_RIGHTS,rights);
            editor.commit();

        }

        public String getUserRights(){
            String r;
            r = pref.getString(KEY_RIGHTS,null);
            return r;
        }

        public String getUserEmail(){
            String email;
            email = pref.getString(KEY_EMAIL,null);
            return email;
        }

        public void logoutUser(){
            editor.clear();
            editor.commit();

            Intent intent = new Intent(_context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
        }
}
