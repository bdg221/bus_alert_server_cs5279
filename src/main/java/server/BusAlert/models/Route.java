package server.BusAlert.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String code;

    @OneToMany
    private List<Stop> stops;

}
