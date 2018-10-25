package me.danbeneventano.weather.util;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import me.danbeneventano.weather.WeatherPlugin;
import org.bukkit.Bukkit;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import java.io.IOException;
import java.util.Optional;

public class Geocoder {
    private GeoApiContext context;

    public Geocoder() {
        context = new GeoApiContext.Builder()
                .apiKey(WeatherPlugin.instance().getConfig().getString("googleMapsApiKey"))
                .build();
    }

    public Optional<GeocodingResult> getLocation(Latitude latitude, Longitude longitude) {
        try {
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(latitude.value(), longitude.value())).await();
            return results.length > 0 ? Optional.of(results[0]) : Optional.empty();
        } catch (ApiException e) {
            Bukkit.getLogger().warning("Google Maps API error. Check your key and permissions.");
            return Optional.empty();
        } catch (InterruptedException | IOException e) {
            return Optional.empty();
        }
    }

    public Optional<GeocodingResult> getLocation(String locationString) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, locationString).await();
            return results.length > 0 ? Optional.of(results[0]) : Optional.empty();
        } catch (ApiException e) {
            Bukkit.getLogger().severe("Invalid Google Maps API key. Disabling Weather plugin.");
            Bukkit.getServer().getPluginManager().disablePlugin(WeatherPlugin.instance());
            return Optional.empty();
        } catch (InterruptedException | IOException e) {
            return Optional.empty();
        }
    }
}
