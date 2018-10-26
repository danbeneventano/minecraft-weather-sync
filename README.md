# minecraft-weather-sync
Spigot plugin that converts in-game coordinates to real coordinates then synchronizes in-game weather with real life weather at that location. The circumference of the globe is 30,000 blocks.

## Commands
* /weather - show weather at your location
* /weather update - update your location instantly (location updates every ten minutes otherwise)
* /weather teleport <location> - teleport to where a real location would be in-game

## Screenshots

![/weather](https://github.com/danbeneventano/minecraft-weather-sync/blob/master/screenshot.png?raw=true)
![/forecast](https://github.com/danbeneventano/minecraft-weather-sync/blob/master/screenshot2.png?raw=true)

## Obtaining API keys

* [Sign up for Dark Sky here](https://darksky.net/dev)
* [Create a Google Maps API key](https://cloud.google.com/maps-platform/#get-started)

## Thanks to
* [google-maps-services-java](https://github.com/googlemaps/google-maps-services-java)
* [darksky-forecast-api](https://github.com/200Puls/darksky-forecast-api)
