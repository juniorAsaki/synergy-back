package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.VisitorReservation;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorVeservationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitorReservationRepository extends JpaRepository<VisitorReservation, Long> {
    Optional<VisitorReservation> findBySlug(String slug);

    List<VisitorVeservationDTO> findByResidenceId(Long residenceId);
}
