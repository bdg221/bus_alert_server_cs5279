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

    public Route addRoute(String code){
        Route route = new Route(code);
        return routeRepository.save(route);
    }

    public Route modifyRoute(Long Id, String code){
        Optional<Route> checkRoute = routeRepository.findById(Id);
        if(checkRoute.isEmpty()){
            return null;
        }
        Route route = checkRoute.get();
        route.setCode(code);
        return routeRepository.save(route);
    }

}
