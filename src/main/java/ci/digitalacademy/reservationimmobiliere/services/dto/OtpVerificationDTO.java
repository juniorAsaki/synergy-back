package ci.digitalacademy.reservationimmobiliere.services.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationDTO {

    private String email;
    private Integer otpCode;

}
