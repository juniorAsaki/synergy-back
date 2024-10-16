package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.models.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long idPerson;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String gender;
    private String slug;

}
