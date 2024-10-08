package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private String comment;
    private LocalDate date;
    private String slug;

    private CustomerDTO customer;
    private ResidenceDTO residence;
}
