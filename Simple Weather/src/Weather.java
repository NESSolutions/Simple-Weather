import java.io.*;
import java.net.*;

import com.google.gson.*;

public class Weather {
    public static void main(String args[]) {
        String rawAddress = getAddress();
        String parsedAddress = addressUrlParser(rawAddress);
        URL addressJsonUrl = createUrl(parsedAddress);
        URLConnection addressJsonConnection = createConnection(addressJsonUrl);
        String addressJsonData = getConnectionData(addressJsonConnection);
        AddressData addressData = parseAddressData(addressJsonData);
        String formatted_address = addressData.results.get(0).formatted_address;
        String lat = addressData.results.get(0).geometry.location.lat;
        String lng = addressData.results.get(0).geometry.location.lng;

        URL weatherUrl = createWeatherUrl(lat, lng);
        URLConnection weatherJsonConnection = createConnection(weatherUrl);
        String weatherJsonData = getConnectionData(weatherJsonConnection);
        WeatherData weatherData = parseWeatherData(weatherJsonData);

        displayData(formatted_address, weatherData);
    }

    private static String getAddress() {
        StringBuilder address = new StringBuilder();
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter sysOut = new PrintWriter(System.out, true);

            sysOut.println("Enter an address: ");
            address.append(userInput.readLine());

        } catch (IOException e) {
            System.out.println(e);
        }

        return address.toString();
    }

    private static String addressUrlParser(String rawAddress) {
        String mapsUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        String parsedAddress = mapsUrl + rawAddress.replace(" ", "+");

        return parsedAddress;
    }

    private static URL createUrl(String urlAddress) {
        URL addressUrl = null;

        try {
            addressUrl = new URL(urlAddress);
        } catch (MalformedURLException e) {
        }

        return addressUrl;
    }

    private static URLConnection createConnection(URL connectionUrl) {
        URLConnection urlConnection = null;

        try {
            urlConnection = connectionUrl.openConnection();
        } catch (IOException e) {
        }

        return urlConnection;
    }

    private static String getConnectionData(URLConnection addressConnection) {
        StringBuilder addressData = new StringBuilder();

        try {
            BufferedReader connInput = new BufferedReader(new InputStreamReader(addressConnection.getInputStream()));
            String temp;
            while ((temp = connInput.readLine()) != null) {
                addressData.append(temp);
            }
        } catch (IOException e) {
        }

        return addressData.toString();
    }

    private static AddressData parseAddressData(String addressJsonData) {
        Gson addressGson = new Gson();
        AddressData addressData = addressGson.fromJson(addressJsonData, AddressData.class);
        return addressData;
    }

    private static URL createWeatherUrl(String lat, String lng) {
        URL weatherUrl = null;

        try {
            weatherUrl = new URL("https://api.weather.gov/points/" + lat + "," + lng + "/forecast");
        } catch (MalformedURLException e) {
        }

        return weatherUrl;
    }

    private static WeatherData parseWeatherData(String weatherJsonData) {
        WeatherData weatherData = null;

        Gson weatherGson = new Gson();
        weatherData = weatherGson.fromJson(weatherJsonData, WeatherData.class);

        return weatherData;
    }

    private static void displayData(String formatted_address, WeatherData weatherData) {
        System.out.println("The Weather for " + formatted_address + " is: ");
        System.out.println(weatherData.properties.periods.get(0).detailedForecast);
        System.out.println("The current temperature is: " + weatherData.properties.periods.get(0).temperature +
                weatherData.properties.periods.get(0).temperatureUnit);
    }
}
