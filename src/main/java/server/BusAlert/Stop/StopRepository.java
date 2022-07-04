package server.BusAlert.Stop;

import org.springframework.data.repository.CrudRepository;
import server.BusAlert.Stop.Stop;

import java.util.List;

public interface StopRepository extends CrudRepository<Stop, Long> {
    @Override
    List<Stop> findAll();

}
