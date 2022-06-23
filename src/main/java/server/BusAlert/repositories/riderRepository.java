package server.BusAlert.repositories;

import org.springframework.data.repository.CrudRepository;
import server.BusAlert.models.Rider;

import java.util.List;

public interface riderRepository extends CrudRepository<Rider, Long> {
    @Override
    List<Rider> findAll();
}
