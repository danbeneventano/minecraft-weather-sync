package me.danbeneventano.weather.util;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import java.util.Optional;

public class Geocoder {
    private GeoApiContext context;

    public Geocoder() {
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCtSkP8u4DiTULzxp1H1Y8aqHyi2oYpUPQ")
                .build();
    }

    public Optional<GeocodingResult> getLocation(Latitude latitude, Longitude longitude) {
        try {
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(latitude.value(), longitude.value())).await();
            return results.length > 0 ? Optional.of(results[0]) : Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<GeocodingResult> getLocation(String locationString) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, locationString).await();
            return results.length > 0 ? Optional.of(results[0]) : Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
