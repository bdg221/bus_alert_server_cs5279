package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.models.Route;
import server.BusAlert.repositories.RouteRepository;

@Service
public class BusAlertService {

    @Autowired
    private RouteRepository routeRepository;

    public Route addRoute(String code){
        Route route = new Route(code);
        return routeRepository.save(route);
    }

}
