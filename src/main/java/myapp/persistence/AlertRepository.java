package myapp.persistence;

import myapp.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends CrudRepository<Alert, Long> {

    Iterable<Alert> findByUserUserId(Long userId);
    Iterable<Alert> findByUserUsername(String username);
}