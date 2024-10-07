package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.Customer;
import ci.digitalacademy.reservationimmobiliere.models.Payment;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalAmount;

    private ResidenceDTO residence;
    private PaymentDTO payment;
    private CustomerDTO customer;
}
