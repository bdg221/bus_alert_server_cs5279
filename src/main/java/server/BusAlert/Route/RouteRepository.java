package server.BusAlert.Route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import server.BusAlert.Route.Route;

import java.util.List;

/**
 * This is the Route repository that utilizes the JPA functionality of Spring.
 */
public interface RouteRepository extends JpaRepository<Route, Long> {
    /**
     * Override the findAll method to return a List of Route objects
     * @return list of Route object
     */
    @Override
    List<Route> findAll();

    /**
     * The findByShortCode method allows for searching the Route table by
     * shortCode value.
     * @param shortCode - shortCode used in the database query
     * @return a List of Route objects found in the table
     */
    List<Route> findByShortCode(String shortCode);
}
