package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.enumeration.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserAndOwnerDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String birthDay;
    private Gender gender;
    private String password;
    private String slug;


}
