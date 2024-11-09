package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWaveDTO {

    private Long id;
    private Double amount;
    private String paymentMethod;
    private LocalDate date;
    private ReservationDTO reservation;
    private String slug;
}
