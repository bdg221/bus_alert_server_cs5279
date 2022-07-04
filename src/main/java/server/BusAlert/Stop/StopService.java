package server.BusAlert.Stop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.Route.RouteService;
import server.BusAlert.Route.Route;

import java.util.List;
import java.util.Optional;

@Service
public class StopService {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private RouteService routeService;

    public List<Stop> getStops(){
        return stopRepository.findAll();
    }

    public Stop getStop(Long Id){
        return getStopFromRepository(Id);
    }

    public Stop addStop(
            String shortcode,
            Float longitude,
            Float latitude,
            Long routeId
    ){
        Route route = routeService.getRoute(routeId);
        Stop stop = new Stop(shortcode, longitude, latitude, route);
        route.addStop(stop);
        return stopRepository.save(stop);
    }

    public Stop modifyStop(
            Long Id,
            String shortcode,
            Float longitude,
            Float latitude,
            Long routeId
    ){
        Stop stop = getStopFromRepository(Id);
        if (stop != null){
            if (shortcode != null) stop.setShortCode(shortcode);
            if (longitude != null) stop.setLongitude(longitude);
            if (latitude != null) stop.setLatitude(latitude);
            if (routeId != null){
                if(stop.getRoute() != null ){
                    stop.getRoute().deleteStop(stop);
                }
                Route new_route = routeService.getRoute(routeId);
                new_route.addStop(stop);
                stop.setRoute(new_route);
            }
            return stopRepository.save(stop);
        }
        return null;
    }

    public void deleteStop(Long Id){
        Stop stop = getStopFromRepository(Id);
        if(stop.getRoute() != null) stop.getRoute().deleteStop(stop);
        stopRepository.delete(stop);
    }

    private Stop getStopFromRepository(Long Id){
        Optional<Stop> checkStop = stopRepository.findById(Id);
        return (checkStop.isEmpty() ?  null : checkStop.get());
    }



}
