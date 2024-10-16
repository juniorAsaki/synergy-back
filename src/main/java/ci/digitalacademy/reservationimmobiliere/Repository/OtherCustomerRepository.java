package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.OtherCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtherCustomerRepository extends JpaRepository<OtherCustomer, Long> {
    Optional<OtherCustomer> findBySlug(String slug);
}
