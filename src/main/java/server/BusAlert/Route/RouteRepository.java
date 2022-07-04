package server.BusAlert.Route;

import org.springframework.data.repository.CrudRepository;
import server.BusAlert.Route.Route;

import java.util.List;

public interface RouteRepository extends CrudRepository<Route, Long> {
    @Override
    List<Route> findAll();

    List<Route> findByShortCode(String shortCode);
}
