package server.BusAlert.Rider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.Route.Route;
import server.BusAlert.Stop.Stop;
import server.BusAlert.Stop.StopService;

import java.util.List;
import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private StopService stopService;

    public List<Rider> getRiders(){
        return riderRepository.findAll();
    }

    public Rider getRider(Long Id){
        return getRiderFromRepository(Id);
    }

    public Rider addRider(
            String phone,
            Long stopId
    ){
        Stop stop = stopService.getStop(stopId);
        Rider rider = new Rider(phone, stop);
        rider = riderRepository.save(rider);
        stop.addRider(rider);
        return rider;
    }

    public Rider modifyRider(
            Long Id,
            String phone,
            Long stopId
    ){
        Rider rider = getRiderFromRepository(Id);
        if(rider == null) return null;
        if (phone != null) rider.setPhone(phone);
        if (stopId != null){
            if(rider.getStop() != null ){
                rider.getStop().deleteRider(rider);
            }
            Stop new_stop = stopService.getStop(stopId);
            new_stop.addRider(rider);
            rider.setStop(new_stop);
        }
        return riderRepository.save(rider);
    }

    public void deleteRider(
            Long Id
    ){
        Rider rider = getRiderFromRepository(Id);
        if(rider != null) {
            if(rider.getStop() != null){
                rider.getStop().deleteRider(rider);
            }
            riderRepository.delete(rider);
        }
    }

    private Rider getRiderFromRepository(Long Id){
        Optional<Rider> checkRider = riderRepository.findById(Id);
        return (checkRider.isEmpty() ?  null : checkRider.get());
    }

}
