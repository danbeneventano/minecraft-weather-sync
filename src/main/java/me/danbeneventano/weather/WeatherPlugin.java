package me.danbeneventano.weather;

import me.danbeneventano.weather.cmd.WeatherCommand;
import me.danbeneventano.weather.listener.PlayerListener;
import me.danbeneventano.weather.player.WeatherPlayerManager;
import me.danbeneventano.weather.util.ForecastRequester;
import me.danbeneventano.weather.util.Geocoder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WeatherPlugin extends JavaPlugin {
    private static WeatherPlugin instance;

    public static WeatherPlugin instance() {
        return instance;
    }

    private WeatherPlayerManager weatherPlayerManager;
    private Geocoder geocoder;
    private ForecastRequester forecastRequester;
    private WeatherUpdater weatherUpdater;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        weatherPlayerManager = new WeatherPlayerManager();
        geocoder = new Geocoder();
        forecastRequester = new ForecastRequester();
        weatherUpdater = new WeatherUpdater();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getOnlinePlayers().forEach(player -> weatherPlayerManager.addWeatherPlayer(player));
        getCommand("weather").setExecutor(new WeatherCommand());
    }

    @Override
    public void onDisable() {
        weatherUpdater.cancelTask();
    }

    public WeatherPlayerManager getWeatherPlayerManager() {
        return weatherPlayerManager;
    }

    public Geocoder getGeocoder() {
        return geocoder;
    }

    public ForecastRequester getForecastRequester() {
        return forecastRequester;
    }

    public WeatherUpdater getWeatherUpdater() {
        return weatherUpdater;
    }
}
