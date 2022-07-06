package server.BusAlert.Stop;

import org.springframework.data.repository.CrudRepository;
import server.BusAlert.Stop.Stop;

import java.util.List;

/**
 * This is the Stop repository that utilizes the JPA functionality of Spring.
 */
public interface StopRepository extends CrudRepository<Stop, Long> {
    /**
     * Override the findAll method to return a List of Stop objects
     * @return list of Stop objects
     */
    @Override
    List<Stop> findAll();

}
