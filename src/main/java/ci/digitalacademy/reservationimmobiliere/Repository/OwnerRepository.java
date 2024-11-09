package ci.digitalacademy.reservationimmobiliere.Repository;

import aj.org.objectweb.asm.commons.Remapper;
import ci.digitalacademy.reservationimmobiliere.models.Owner;
import ci.digitalacademy.reservationimmobiliere.models.User;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findBySlug(String slug);

    Optional<Owner> findByUserId(Long id);
}
