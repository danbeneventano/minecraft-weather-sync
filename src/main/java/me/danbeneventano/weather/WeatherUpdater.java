package me.danbeneventano.weather;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

public class WeatherUpdater {
    private BukkitTask updateTask;

    public WeatherUpdater() {
        updateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(WeatherPlugin.instance(), () -> {
            WeatherPlugin.instance().getWeatherPlayerManager().getOnlineWeatherPlayers().forEach(wp -> {
                Location l = Bukkit.getPlayer(wp.getUuid()).getLocation();
                wp.updatePersonalWeather(l);
            });
        }, 20L, 20L * TimeUnit.MINUTES.toSeconds(10));
    }

    public void cancelTask() {
        updateTask.cancel();
    }
}
