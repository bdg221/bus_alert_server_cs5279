package server.BusAlert.models;

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

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}
