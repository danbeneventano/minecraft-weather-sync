package me.danbeneventano.weather.util;

import com.google.maps.model.LatLng;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

public class CoordinateCalculator {
    public static Latitude gameXToLatitude(double gameX) {
        while (gameX > 30_000.0) gameX -= 30_000.0;
        while (gameX < -30_000.0) gameX += 30_000.0;
        double realCoord = 90 * gameX / 30_000.0;
        return new Latitude(realCoord);
    }

    public static Longitude gameZToLongitude(double gameZ) {
        while (gameZ > 30_000.0) gameZ -= 30_000.0;
        while (gameZ < -30_000.0) gameZ += 30_000.0;
        double realCoord = 180 * gameZ / 30_000.0;
        return new Longitude(realCoord);
    }

    public static double latitudeToGameX(LatLng latLng) {
        return 30_000.0 * latLng.lat / 90;
    }

    public static double longitudeToGameZ(LatLng latLng) {
        return 30_000.0 * latLng.lng / 180;
    }
}
