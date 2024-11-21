package ci.digitalacademy.reservationimmobiliere.services.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private Integer otpCode;
    private LocalDateTime otpExpirationDate;
    private boolean verified;
    private boolean isActivated;

    private RoleDTO role;

}
