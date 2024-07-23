package di.repository.boat;

import di.model.entity.boats.AbstractBoat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository extends JpaRepository<AbstractBoat, Long> {
}
