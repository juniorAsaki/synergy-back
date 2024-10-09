package ci.digitalacademy.reservationimmobiliere.Repository;

import aj.org.objectweb.asm.commons.Remapper;
import ci.digitalacademy.reservationimmobiliere.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional <Reservation> findBySlug(String slug);
}
