package com.mtechcomm.nanocreditnative.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class GetRequestToken extends AsyncTask<Void, Void, String> {

    Context context;
//    private ProgressDialog mProgressDialog;

    public GetRequestToken(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        mProgressDialog = ProgressDialog.show(
//                context,
//                "Please wait", // Title
//                "Preparing Request",
//                true);

    }

    @Override
    protected String doInBackground(Void... aVoid) {

        try {


            String urlStr = "https://nlp-gh.oncustomer.tech/api/authentication";
            // Set url
//                String urlStr = "https://nlp-mtech.oncustomer.tech/api/authentication";
            URL url = new URL(urlStr);
            //URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // If secure connection
            if (urlStr.startsWith("https")) {
                try {
                    SSLContext sc;
                    sc = SSLContext.getInstance("TLS");
                    sc.init(null, null, new java.security.SecureRandom());

                    ((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    Log.d("SSL Costruct", "Failed to construct SSL object", e);
                }
            }

            // My basic authentication
            String user = "ussd";
            String password = "ussd1234:SHARE";
            String businnessUnitCode = "SHARE";

            String userPass = user + ":" + password+ ":" + businnessUnitCode;
            String basicAuth = "Basic " + Base64.encodeToString(userPass.getBytes(), Base64.NO_WRAP);
            conn.setRequestProperty("Authorization", "Basic dXNzZDp1c3NkMTIzNDpTSEFSRQ==");


            int timeoutConnection = 3000;
            int timeoutSocket = 5000;

            // Set Timeout and method
            conn.setReadTimeout(timeoutConnection);
            conn.setConnectTimeout(timeoutSocket);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            conn.connect();
            String response;


            int responseCode = conn.getResponseCode(); //can call this instead of con.connect()
            if (responseCode >= 400 && responseCode <= 499) {
                throw new Exception("Bad authentication status: " + responseCode); //provide a more meaningful exception message
            } else {
//                    InputStream in = client.getInputStream();
                //etc...
                InputStream inputStream = conn.getInputStream();
                response = convertStreamToString(inputStream);
            }

//                Log.d("Basic Auth", "doInBackground: " + basicAuth);
            Log.d("JSON Message", "onClick: " + response);


            return response;

        } catch (Exception e) {

            Log.d("Error Message", "onClick: " + e.getMessage());
            Log.d("Error 2", "onClick: " + e.toString());
            return null;
        }


    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
        if (result != null) {
            Log.d("Request Response", "onClick: " + result);


//            SharedPreferences sharedPreferences = .getSharedPreferences(fileNameString, MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("token", result);
//            editor.commit();

//                new checkCustomer(doc, result).execute();

        } else {
            Toast.makeText(context, "Oops!!! something has gone wrong", Toast.LENGTH_SHORT).show();
        }


    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
}
