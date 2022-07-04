package server.BusAlert.Rider;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RiderRepository extends CrudRepository<Rider, Long> {
    @Override
    List<Rider> findAll();
}
