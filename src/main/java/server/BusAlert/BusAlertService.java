package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.models.Route;
import server.BusAlert.repositories.RouteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BusAlertService {

    @Autowired
    private RouteRepository routeRepository;


    public List<Route> getRoutes(){
        return routeRepository.findAll();
    }

    public Route getRoute(Long Id){
        return getRouteFromRepository(Id);
    }

    public Route addRoute(String shortCode){
        Route route = new Route(shortCode);
        return routeRepository.save(route);
    }

    public Route modifyRoute(Long Id, String shortCode){
        Route route = getRouteFromRepository(Id);
         if(route == null){
            return null;
        }
        route.setShortCode(shortCode);
        return routeRepository.save(route);
    }

    public void deleteRoute(Long Id){
        routeRepository.deleteById(Id);
    }


    private Route getRouteFromRepository(Long Id){
        Optional<Route> checkRoute = routeRepository.findById(Id);
        return (checkRoute.isEmpty() ?  null : checkRoute.get());
    }

    private Route getRouteFromShort_Code(String shortCode){
        List<Route> checkRoute = routeRepository.findByShortCode(shortCode);
        return (checkRoute.isEmpty() ?  null : checkRoute.get(0));
    }

}
