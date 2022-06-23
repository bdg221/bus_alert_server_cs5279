package server.BusAlert.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String code;

    @ManyToOne
    private Route route;

    @OneToMany
    private List<Rider> riders;

}
