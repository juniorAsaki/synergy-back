package ci.digitalacademy.reservationimmobiliere.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PictureResidenceDTO {

    private Long id;
    private String imageUrl;
    @JsonIgnore
    private ResidenceDTO residence;
    private String slug;
}
