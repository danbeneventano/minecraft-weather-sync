package me.danbeneventano.weather.util;

import me.danbeneventano.weather.WeatherPlugin;
import org.bukkit.Bukkit;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class ForecastRequester {
    private DarkSkyJacksonClient client;

    public ForecastRequester() {
        client = new DarkSkyJacksonClient();
    }

    public Optional<Forecast> getForecast(Latitude latitude, Longitude longitude) {
        try {
            return Optional.of(client.forecast(generateRequest(latitude, longitude)));
        } catch (ForecastException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("Could not retrieve forecast from Dark Sky. Is your API key valid?");
            return Optional.empty();
        }
    }

    public Optional<Forecast> getForecast(ForecastRequest request) {
        try {
            return Optional.of(client.forecast(request));
        } catch (ForecastException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("Could not retrieve forecast from Dark Sky. Is your API key valid?");
            return Optional.empty();
        }
    }

    public ForecastRequest generateRequest(Latitude latitude, Longitude longitude) {
        return new ForecastRequestBuilder()
                .key(new APIKey(WeatherPlugin.instance().getConfig().getString("darkSkyApiKey")))
                .location(new GeoCoordinates(longitude, latitude))
                .units(ForecastRequestBuilder.Units.us)
                .language(ForecastRequestBuilder.Language.en).build();
    }

    public ForecastRequest generateRequest(Latitude latitude, Longitude longitude, int daysAhead) {
        return new ForecastRequestBuilder()
                .key(new APIKey(WeatherPlugin.instance().getConfig().getString("darkSkyApiKey")))
                .location(new GeoCoordinates(longitude, latitude))
                .units(ForecastRequestBuilder.Units.us)
                .time(Instant.now().plus(daysAhead, ChronoUnit.DAYS))
                .language(ForecastRequestBuilder.Language.en).build();
    }
}
