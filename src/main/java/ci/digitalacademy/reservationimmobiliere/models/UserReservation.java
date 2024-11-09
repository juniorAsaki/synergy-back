package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserReservation extends Reservation{

    @ManyToOne
    private Customer customer;
}
