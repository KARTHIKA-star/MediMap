package com.example.medimap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlaceFetcher extends AsyncTask<String, Void, String> {

    private GoogleMap mMap;
    private Context mContext;  // Adding context to access resources

    public PlaceFetcher(GoogleMap mMap) {
        this.mMap = mMap;
        Context context = null;
        this.mContext = context;  // Initialize context
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        StringBuilder result = new StringBuilder();

        try {
            // Create the URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            int read;
            while ((read = reader.read()) != -1) {
                result.append((char) read);
            }

        } catch (Exception e) {
            Log.e("PlaceFetcher", "Error in API call", e);
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonResponse = new JSONObject(result);
            JSONArray results = jsonResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject place = results.getJSONObject(i);
                String name = place.getString("name");
                JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                // Set marker color based on place type
                LatLng placeLocation = new LatLng(lat, lng);
                String placeType = place.getJSONArray("types").getString(0); // To distinguish between pharmacy and hospital

                if (placeType.equals("pharmacy")) {
                    // Create a custom larger marker for pharmacies
                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_41);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                    mMap.addMarker(new MarkerOptions()
                            .position(placeLocation)
                            .title(name)
                            .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap)));
                } else if (placeType.equals("hospital")) {
                    // Create a regular-sized marker for hospitals
                    mMap.addMarker(new MarkerOptions()
                            .position(placeLocation)
                            .title(name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
            }
        } catch (Exception e) {
            Log.e("PlaceFetcher", "Error parsing API response", e);
        }
    }
}
