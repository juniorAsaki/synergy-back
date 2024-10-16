package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedPersonDTO extends PersonDTO{


    private UserDTO user;

    private AddressDTO address;
}
