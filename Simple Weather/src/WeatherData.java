import java.util.ArrayList;

public class WeatherData {
    Properties properties;

    public WeatherData() {

    }
}

class Properties {
    ArrayList<Periods> periods;

    public Properties() {

    }
}

class Periods {
    String name;
    String startTime;
    String endTime;
    String temperature;
    String temperatureUnit;
    String windSpeed;
    String windDirection;
    String icon;
    String shortForecast;
    String detailedForecast;

    public Periods() {

    }
}