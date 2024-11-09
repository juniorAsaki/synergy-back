package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User_reservationDTO extends ReservationDTO{

    private CustomerDTO customer;
}
