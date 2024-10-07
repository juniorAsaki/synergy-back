package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.Reservation;

import java.util.Set;

public class CustomerDTO extends PersonDTO{

    private Set<ReservationDTO> reservations;
}
