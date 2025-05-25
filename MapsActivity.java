package com.example.medimap;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SearchView searchView;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize Places API
        Places.initialize(getApplicationContext(), "AIzaSyB2E7fbMXkIUs1VcHTeWb5xkdDM8k9iA38");
        placesClient = Places.createClient(this);

        // Initialize SearchView
        searchView = findViewById(R.id.searchView);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchLocation(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        } else {
            Toast.makeText(this, "SearchView not found in layout", Toast.LENGTH_SHORT).show();
        }

        // Initialize Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Map fragment not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Highlight the searched location with a custom marker
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Searched Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                findNearbyPlaces(latLng); // Fetch nearby pharmacies and hospitals
            } else {
                Toast.makeText(this, "Location not found!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findNearbyPlaces(LatLng latLng) {
        String pharmacyType = "pharmacy";
        String hospitalType = "hospital";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=" + latLng.latitude + "," + latLng.longitude +
                "&radius=2000" + // 2km radius
                "&types=" + pharmacyType + "|" + hospitalType +
                "&key=AIzaSyB2E7fbMXkIUs1VcHTeWb5xkdDM8k9iA38"; // Replace with your API key

        new PlaceFetcher(mMap).execute(url); // AsyncTask to fetch both pharmacy and hospital places
    }
}
