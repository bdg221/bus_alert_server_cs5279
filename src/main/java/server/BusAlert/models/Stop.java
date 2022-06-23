package server.BusAlert.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToMany(mappedBy = "stop")
    private List<Rider> riders = new ArrayList<>();

    public Stop() {
    }

    public Stop(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setRiders(List<Rider> riders) {
        this.riders = riders;
    }
}
