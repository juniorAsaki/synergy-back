package ci.digitalacademy.reservationimmobiliere.services.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CustomerDTO extends PersonDTO{

    private String email;
    private Set<ReservationDTO> reservations;
}
