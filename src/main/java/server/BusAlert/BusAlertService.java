package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.models.Route;
import server.BusAlert.repositories.RouteRepository;

import java.util.Optional;

@Service
public class BusAlertService {

    @Autowired
    private RouteRepository routeRepository;


    public Route getRoute(Long Id){
        return getRouteFromRepository(Id);
    }

    public Route addRoute(String code){
        Route route = new Route(code);
        return routeRepository.save(route);
    }

    public Route modifyRoute(Long Id, String code){
        Route route = getRouteFromRepository(Id);
         if(route == null){
            return null;
        }
        route.setCode(code);
        return routeRepository.save(route);
    }

    public Route deleteRoute(Long Id){
        Route route = getRouteFromRepository(Id);
        routeRepository.deleteById(Id);
        return route;
    }


    private Route getRouteFromRepository(Long Id){
        Optional<Route> checkRoute = routeRepository.findById(Id);
        return (checkRoute.isEmpty() ?  null : checkRoute.get());
    }

}
