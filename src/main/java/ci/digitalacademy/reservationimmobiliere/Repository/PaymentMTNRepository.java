package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.Payment;
import ci.digitalacademy.reservationimmobiliere.models.PaymentMTN;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMTNRepository extends JpaRepository<PaymentMTN, Long> {
}
