package com.mtechcomm.nanocreditnative.classes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class NetworkProviderClass {

    Context context;
    String TAG;

    private static final String TAG2 = NetworkProviderClass.class.getSimpleName();

    public NetworkProviderClass(Context context, String tag){
        this.context = context;
        this.TAG = tag;
    }

    public String getNetwork(String phoneNumber) {
        String sub = "";

        Log.d(TAG, TAG2 + ": " + phoneNumber);

        if (!phoneNumber.startsWith("+")) {
            if (!phoneNumber.startsWith("2")){
                sub = phoneNumber.substring(0, 3);
            }else{
                sub = phoneNumber.substring(3, 4);
                sub = "0" + sub;
            }

        } else {
            sub = phoneNumber.substring(4, 5);
            sub = "0" + sub;
        }

        Log.d(TAG, TAG2 + ": " + sub);

        String network = null;
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("networks");

            Log.d(TAG, TAG2 + ": " + m_jArry.toString());

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject eachNetwork = m_jArry.getJSONObject(i);

                Log.d(TAG, TAG2 + ": " + eachNetwork.toString());

                String selectedNetwork = eachNetwork.getString("name");
//                ArrayList<String> selectedCodes = (ArrayList<String>) eachNetwork.get("codes");
                JSONArray selectedCodes = eachNetwork.getJSONArray("codes");

                Log.d(TAG, TAG2 +  " Codes : " + selectedCodes);

                for (int j = 0; j < selectedCodes.length(); j++) {
                    String particularSub = selectedCodes.getString(j);
                    Log.d(TAG, TAG2 +  " Part : " + particularSub);
                    if (particularSub.equals(sub)) {
                        network = selectedNetwork;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return network;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("numbers_and_networks.json");
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
