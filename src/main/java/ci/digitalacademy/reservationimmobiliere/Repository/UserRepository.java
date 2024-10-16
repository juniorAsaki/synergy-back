package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findIdByEmail(String email);

    Optional<User> findByEmail(String userEmail);
}
