package server.BusAlert.models;

import javax.persistence.*;

@Entity
public class Rider {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;

    private String phone;

    @ManyToOne
    private Stop stop;
}
