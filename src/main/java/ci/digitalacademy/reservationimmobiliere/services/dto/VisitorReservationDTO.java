package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorReservationDTO extends ReservationDTO{

    private OtherCustomerDTO otherCustomer;
}
