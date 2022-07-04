package server.BusAlert.Rider;

import server.BusAlert.Stop.Stop;

import javax.persistence.*;

@Entity
public class Rider {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "stop_id")
    private Stop stop;

    public Rider() {
    }

    public Rider(String phone, Stop stop) {
        this.phone = phone;
        this.stop = stop;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }
}