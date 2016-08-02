package pl.droidsonroids.hodor;

import android.content.Context;
import android.content.SharedPreferences;

public class HodorPreferences {
    public SharedPreferences mSharedPreferences;

    public HodorPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getUsername() {
        return mSharedPreferences.getString(Constants.USER_NAME, "");
    }

    public void setUsername(String username) {

        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(Constants.USER_NAME, username);
        mEditor.apply();
    }
}
