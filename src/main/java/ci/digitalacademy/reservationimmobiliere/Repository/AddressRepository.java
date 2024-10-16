package ci.digitalacademy.reservationimmobiliere.Repository;

import aj.org.objectweb.asm.commons.Remapper;
import ci.digitalacademy.reservationimmobiliere.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findBySlug(String slug);
}
