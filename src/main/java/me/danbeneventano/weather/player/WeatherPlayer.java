package me.danbeneventano.weather.player;

import com.google.maps.model.GeocodingResult;
import me.danbeneventano.weather.WeatherPlugin;
import me.danbeneventano.weather.util.CoordinateCalculator;
import me.danbeneventano.weather.util.CustomWeatherType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import java.util.Optional;
import java.util.UUID;

public class WeatherPlayer {
    private UUID uuid;
    private Location gameLocation;
    private Latitude latitude;
    private Longitude longitude;
    private String locationString = "";
    private Forecast forecast;
    private CustomWeatherType weatherType = CustomWeatherType.CLEAR;

    public WeatherPlayer(UUID uuid, Location gameLocation) {
        this.uuid = uuid;
        this.gameLocation = gameLocation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void updatePersonalWeather(Location gameLocation) {
        this.gameLocation = gameLocation;
        this.latitude = CoordinateCalculator.gameXToLatitude(gameLocation.getX());
        this.longitude = CoordinateCalculator.gameZToLongitude(gameLocation.getZ());
        WeatherPlugin.instance().getForecastRequester().getForecast(latitude, longitude).ifPresent(forecast -> {
            this.forecast = forecast;
            retrieveLocationString();
            weatherType = CustomWeatherType.fromIcon(forecast.getCurrently().getIcon());
            Player player = Bukkit.getPlayer(uuid);
            if (weatherType != CustomWeatherType.CLEAR) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            } else {
                player.setPlayerWeather(WeatherType.CLEAR);
            }
        });
    }

    public String retrieveLocationString() {
        Optional<GeocodingResult> result = WeatherPlugin.instance().getGeocoder().getLocation(latitude, longitude);
        if (result.isPresent()) {
            locationString = result.get().formattedAddress;
        } else {
            locationString = "Unknown";
        }
        return locationString;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public CustomWeatherType getWeatherType() {
        return weatherType;
    }

    public Location getGameLocation() {
        return gameLocation;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }
}
