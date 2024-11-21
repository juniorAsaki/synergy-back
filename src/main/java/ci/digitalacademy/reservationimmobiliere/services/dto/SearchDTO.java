package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {

    private BigDecimal price;
    private String name;
    private String city;
    private String district;
}
