package com.mtechcomm.nanocreditnative.classes;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhoneNumberChoice {
    Context context;
    String TAG;

    public PhoneNumberChoice(Context context, String TAG) {
        this.context = context;
        this.TAG = TAG;
    }

    public ArrayList<PhoneNumberClass> getCountryAndCodes() {
        ArrayList<PhoneNumberClass> phoneNumberClasses = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("country_codes");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                PhoneNumberClass phoneNumberClass = new PhoneNumberClass(object.getString("_id"), object.getString("country"), object.getString("code"));
                phoneNumberClasses.add(phoneNumberClass);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return phoneNumberClasses;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("country_phone_codes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
