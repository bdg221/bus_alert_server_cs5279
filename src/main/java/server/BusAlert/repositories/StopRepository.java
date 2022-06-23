package server.BusAlert.repositories;

import org.springframework.data.repository.CrudRepository;
import server.BusAlert.models.Stop;

import java.util.List;

public interface StopRepository extends CrudRepository<Stop, Long> {
    @Override
    List<Stop> findAll();

}
