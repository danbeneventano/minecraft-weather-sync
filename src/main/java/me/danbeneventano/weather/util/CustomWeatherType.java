package me.danbeneventano.weather.util;

public enum CustomWeatherType {
    SNOW,
    RAIN,
    THUNDERSTORM,
    CLEAR;

    public static CustomWeatherType fromIcon(String icon) {
        // clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        switch (icon) {
            case "rain":
                return RAIN;
            case "snow":
            case "sleet":
                return SNOW;
            default:
                return CLEAR;
        }
    }
}
