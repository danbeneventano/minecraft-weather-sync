package me.danbeneventano.weather.cmd;

import com.google.common.collect.Lists;
import me.danbeneventano.weather.WeatherPlugin;
import me.danbeneventano.weather.player.WeatherPlayer;
import me.danbeneventano.weather.util.ForecastRequester;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.plogitech.darksky.forecast.model.Forecast;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static net.md_5.bungee.api.ChatColor.*;

public class ForecastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        WeatherPlayer wp = WeatherPlugin.instance().getWeatherPlayerManager().getWeatherPlayer(player.getUniqueId()).get();
        List<Forecast> forecasts = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            forecasts.add(getForecast(wp, i).get());
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                        .withLocale(Locale.US)
                        .withZone(ZoneId.systemDefault());

        DecimalFormat decimalFormat = new DecimalFormat("###");

        player.sendMessage(AQUA + ITALIC.toString() + "Forecast for " + wp.retrieveLocationString() + ":");
        for (Forecast forecast : forecasts) {
            String day = forecast.getCurrently().getTime().atZone(ZoneId.systemDefault()).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            String date = formatter.format(forecast.getCurrently().getTime());
            player.sendMessage(BLUE + day + " " + date + ": " + WHITE + decimalFormat.format(forecast.getCurrently().getTemperature()) + "\u2109 - " + forecast.getCurrently().getSummary());
        }
        return true;
    }

    private Optional<Forecast> getForecast(WeatherPlayer wp, int daysAhead) {
        ForecastRequester requester = WeatherPlugin.instance().getForecastRequester();
        return requester.getForecast(requester.generateRequest(wp.getLatitude(), wp.getLongitude(), daysAhead));
    }
}
