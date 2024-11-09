package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.UserReservation;
import ci.digitalacademy.reservationimmobiliere.services.dto.User_reservationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReservationRepository extends JpaRepository<UserReservation, Long> {
    Optional<UserReservation> findBySlug(String slug);

    List<User_reservationDTO> findByResidenceId(Long residenceId);
}
