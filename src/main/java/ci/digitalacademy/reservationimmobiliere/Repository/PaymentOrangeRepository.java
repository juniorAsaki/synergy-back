package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.Payment;
import ci.digitalacademy.reservationimmobiliere.models.PaymentOrange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrangeRepository extends JpaRepository<PaymentOrange, Long> {
}
