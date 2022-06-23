package server.BusAlert.repositories;

import org.springframework.data.repository.CrudRepository;
import server.BusAlert.models.Route;

import java.util.List;

public interface RouteRepository extends CrudRepository<Route, Long> {
    @Override
    List<Route> findAll();
}