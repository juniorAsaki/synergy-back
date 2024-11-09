package ci.digitalacademy.reservationimmobiliere.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VisitorReservation extends Reservation{

    @ManyToOne
    private OtherCustomer otherCustomer;
}
