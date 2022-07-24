package server.BusAlert.Location;

public class LocationRequest {

    public String routeId;
    public Float latitude;
    public Float longitude;

    public boolean isFirstPing = false;

    public String getRouteId() {
        return routeId;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public boolean isFirstPing() {
        return isFirstPing;
    }
}
