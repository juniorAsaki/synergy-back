package ci.digitalacademy.reservationimmobiliere.services.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerRegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
}
