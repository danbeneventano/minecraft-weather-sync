package me.danbeneventano.weather.util;

import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

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
            return Optional.empty();
        }
    }

    private ForecastRequest generateRequest(Latitude latitude, Longitude longitude) {
        return new ForecastRequestBuilder()
                .key(new APIKey("b3d0589e5a90ee0dcdddb40751724b7a"))
                .location(new GeoCoordinates(longitude, latitude))
                .language(ForecastRequestBuilder.Language.en).build();
    }
}
