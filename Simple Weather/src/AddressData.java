import java.util.ArrayList;

public class AddressData {
    ArrayList<Results> results;

    public AddressData() {

    }
}

class Results {
    String formatted_address;
    Geometry geometry;

    public Results() {

    }
}

class Geometry {
    Location location;

    public Geometry() {

    }
}

class Location {
    String lat;
    String lng;

    public Location() {

    }
}