package server.BusAlert.Rider;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This is the Rider repository that utilizes the JPA functionality of Spring.
 */
public interface RiderRepository extends CrudRepository<Rider, Long> {

    /**
     * The finaAll method is overwritten to return a List of Rider objects
     * @return a list of Rider objects
     */
    @Override
    List<Rider> findAll();
}
