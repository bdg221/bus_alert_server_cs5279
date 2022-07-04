package server.BusAlert.Stop;

import server.BusAlert.models.Rider;
import server.BusAlert.Route.Route;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String shortCode;

    private Float longitude;

    private Float latitude;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToMany(mappedBy = "stop")
    private List<Rider> riders = new ArrayList<>();

    public Stop() {
    }

    public Stop(String shortCode, Float longitude, Float latitude, Route route) {
        this.shortCode = shortCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.route = route;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Rider> getRiders() {
        return riders;
    }

    public void addRider(Rider rider) {
        this.riders.add(rider);
    }

    public void deleteRider(Rider rider) { this.riders.remove(rider); }
 }
