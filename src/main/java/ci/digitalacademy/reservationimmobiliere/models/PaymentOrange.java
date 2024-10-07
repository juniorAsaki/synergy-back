package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "PaymentOrange")
@Getter
@Setter
@Table(name = "payment_orange")
public class PaymentOrange extends Payment{
}
