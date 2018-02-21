package com.blacetec.blindwalls;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BlindWallConnector extends AsyncTask<String, Void, String> {

    private OnBlindWallsAvailable listener = null;

    private static final String TAG = BlindWallConnector.class.getSimpleName();

    public BlindWallConnector(OnBlindWallsAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        BufferedReader bufferedReader = null;
        String response = "";

        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();

            bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            response = bufferedReader.readLine().toString();
            String line;
            while( (line = bufferedReader.readLine()) != null) {
                response += line;
            }

        } catch (Exception e) {
            return null;
        } finally {
            if( bufferedReader != null ) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return response;
    }

    protected void onPostExecute(String response) {
        if(response == null || response == "")
            return;

        try {
            JSONArray blindWalls = new JSONArray(response);

            for(int idx = 0; idx < blindWalls.length(); idx++) {
                JSONObject blindWall = blindWalls.getJSONObject(idx);

                String material = blindWall.getJSONObject("material").getString("en");
                String description = blindWall.getJSONObject("description").getString("en");
                String title = blindWall.getJSONObject("title").getString("en").trim().replaceAll(" +", " ");
                String imageString = "https://api.blindwalls.gallery/" + blindWall.getJSONArray("images").getJSONObject(0).getString("url");
                int id = blindWall.getInt("id");
                String photographer = blindWall.getString("photographer");
                String address = blindWall.getString("address");
                int addressNumber = blindWall.getInt("numberOnMap");

                BlindWall b = new BlindWall(id, title, address, photographer, addressNumber, description, imageString, material);
                listener.OnBlindWallsAvailable(b);
            }
        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }
}
