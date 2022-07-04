package server.BusAlert.Route;

import server.BusAlert.Stop.Stop;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String shortCode;

    @OneToMany(mappedBy = "route")
    private List<Stop> stops = new ArrayList<>();

    public Route() {
    }

    public Route(String shortCode) {
        this.shortCode = shortCode;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    public void deleteStop(Stop stop) { this.stops.remove(stop); }
}
