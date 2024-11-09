package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RequestReservationOtherCustomerDTO extends OtherCustomerDTO{

    private Long residenceId;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;
    private BigDecimal totalAmount;
}
