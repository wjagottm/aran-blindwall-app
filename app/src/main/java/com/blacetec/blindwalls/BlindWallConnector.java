package com.blacetec.blindwalls;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
        String response;

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

                final String material = blindWall.getJSONObject("material").getString("en");
                final String description = blindWall.getJSONObject("description").getString("en");
                final String title = blindWall.getJSONObject("title").getString("en").trim().replaceAll(" +", " ");
                String[] imageString = {"https://api.blindwalls.gallery/" + blindWall.getJSONArray("images").getJSONObject(0).getString("url")};
                final int id = blindWall.getInt("id");
                final String photographer = blindWall.getString("photographer");
                final String address = blindWall.getString("address");
                final int addressNumber = blindWall.getInt("numberOnMap");

                getByteArray getByteArray = new getByteArray(){
                    @Override
                    protected void onPostExecute(Bitmap bmp) {
                        byte[] imageBArray = null;
                        if (bmp != null)
                        {
                            Log.i(TAG, "compressing to ByteArray");
                            super.onPostExecute(bmp);

                            Bitmap bm = bmp;

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 40, bos);
                            imageBArray = bos.toByteArray();
                        } else {
                            Log.i(TAG, "No BMP data found.");
                        }

                        BlindWall b = new BlindWall(id, title, address, photographer, addressNumber, description, imageBArray, material);
                        listener.OnBlindWallsAvailable(b);
                    }
                };

                getByteArray.execute(imageString);


            }
        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }
}
