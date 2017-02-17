package gdghackathon.mogakco.core;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStore {

    public static final String SP_NAME = "LocalData";
    private SharedPreferences sharedPref;

    public String getDaumMapAPIkey() {
        return sharedPref.getString("daum_map_apikey", "4b781301b49fff6c43a6a43a1acff03c");
    }

    public String getStringValue(String key, String defaultValue) {
        return sharedPref.getString(key, defaultValue);
    }

    public void setStringValue(String key, String value) {
        SharedPreferences.Editor spEditor = sharedPref.edit();
        spEditor.putString(key, value);
        spEditor.apply();
    }
    public void clearLoginData() {
        SharedPreferences.Editor spEditor = sharedPref.edit();
        spEditor.remove("token");
        spEditor.apply();
    }

    public LocalStore(Context context) {

        sharedPref = context.getSharedPreferences(SP_NAME, 0);
    }

}
