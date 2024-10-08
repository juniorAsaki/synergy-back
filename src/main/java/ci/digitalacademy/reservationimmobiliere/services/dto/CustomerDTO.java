package ci.digitalacademy.reservationimmobiliere.services.dto;


import java.util.Set;

public class CustomerDTO extends PersonDTO{

    private Set<ReservationDTO> reservations;
}
