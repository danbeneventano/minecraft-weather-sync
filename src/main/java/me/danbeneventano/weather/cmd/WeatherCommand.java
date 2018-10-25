package me.danbeneventano.weather.cmd;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import me.danbeneventano.weather.WeatherPlugin;
import me.danbeneventano.weather.player.WeatherPlayer;
import me.danbeneventano.weather.util.CoordinateCalculator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Optional;

import static net.md_5.bungee.api.ChatColor.*;

public class WeatherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length == 0) {
            WeatherPlayer wp = WeatherPlugin.instance().getWeatherPlayerManager().getWeatherPlayer(player.getUniqueId()).get();
            DecimalFormat format = new DecimalFormat("###.#####");
            sendMessages(player,
                    GRAY + "In-game coordinates: " + YELLOW + "X: " + wp.getGameLocation().getBlockX() + " Y: " + wp.getGameLocation().getBlockY() + " Z: " + wp.getGameLocation().getBlockZ(),
                    GRAY + "Real life coordinates: " + BLUE + "Latitude: " + format.format(wp.getLatitude().value()) + ", Longitude: " + format.format(wp.getLongitude().value()),
                    GRAY + "Real life location: " + GOLD + (wp.retrieveLocationString().isEmpty() ? "Unknown" : wp.retrieveLocationString()),
                    GRAY + "Weather: " + LIGHT_PURPLE + wp.getForecast().getCurrently().getSummary());
        } else if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "/weather teleport <location>");
        } else if (args[0].equalsIgnoreCase("teleport")) {
            String[] locationWords = Arrays.copyOfRange(args, 1, args.length);
            String location = String.join(" ", locationWords);
            Optional<GeocodingResult> result = WeatherPlugin.instance().getGeocoder().getLocation(location);
            if (result.isPresent()) {
                player.sendMessage(ChatColor.DARK_GRAY + "Teleporting to " + ChatColor.GRAY + result.get().formattedAddress);
                LatLng latLng = result.get().geometry.location;
                double x = CoordinateCalculator.latitudeToGameX(latLng);
                double z = CoordinateCalculator.longitudeToGameZ(latLng);
                double y = player.getWorld().getHighestBlockYAt((int) x, (int) z);
                player.teleport(new Location(player.getWorld(), x, y, z));
            } else {
                player.sendMessage(ChatColor.RED + "Invalid location.");
            }
        }

        return true;
    }

    private void sendMessages(Player player, String... messages) {
        for (String message : messages) {
            player.sendMessage(message);
        }
    }
}
