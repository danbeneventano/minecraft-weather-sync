package me.danbeneventano.weather.player;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class WeatherPlayerManager {
    private Set<WeatherPlayer> onlineWeatherPlayers = Sets.newHashSet();

    public Optional<WeatherPlayer> getWeatherPlayer(UUID uuid) {
        return onlineWeatherPlayers.stream().filter(wp -> wp.getUuid().equals(uuid)).findAny();
    }

    public Set<WeatherPlayer> getOnlineWeatherPlayers() {
        return onlineWeatherPlayers;
    }

    public void addWeatherPlayer(Player player) {
        WeatherPlayer weatherPlayer = new WeatherPlayer(player.getUniqueId(), player.getLocation());
        onlineWeatherPlayers.add(weatherPlayer);
        weatherPlayer.updatePersonalWeather(player.getLocation());
    }

    public void removeWeatherPlayer(UUID uuid) {
        onlineWeatherPlayers.removeIf(wp -> wp.getUuid().equals(uuid));
    }
}
