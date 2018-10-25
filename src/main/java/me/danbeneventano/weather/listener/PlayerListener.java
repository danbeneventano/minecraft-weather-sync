package me.danbeneventano.weather.listener;

import me.danbeneventano.weather.WeatherPlugin;
import me.danbeneventano.weather.player.WeatherPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener {
    private WeatherPlayerManager weatherPlayerManager = WeatherPlugin.instance().getWeatherPlayerManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        weatherPlayerManager.addWeatherPlayer(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        weatherPlayerManager.removeWeatherPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        weatherPlayerManager.getWeatherPlayer(event.getPlayer().getUniqueId()).ifPresent(wp -> wp.updatePersonalWeather(event.getTo()));
    }
}
