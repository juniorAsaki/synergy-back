package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByRole(String roleUser);
}
