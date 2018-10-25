package me.danbeneventano.weather.gui;

import me.danbeneventano.weather.player.WeatherPlayer;
import me.danbeneventano.weather.util.gui.GuiMenu;

public class WeatherGui extends GuiMenu {

    public WeatherGui(WeatherPlayer wp) {
        super("Weather - " + wp.retrieveLocationString(), 6, true);
    }
}
