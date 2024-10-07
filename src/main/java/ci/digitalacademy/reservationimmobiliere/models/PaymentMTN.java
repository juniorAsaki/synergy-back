package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "Payment_MTN")
public class PaymentMTN extends Payment{
}
