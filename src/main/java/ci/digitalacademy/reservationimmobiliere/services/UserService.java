package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtpVerificationDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    Optional<UserDTO> getById(Long id);
    void delete(Long id);


    UserDTO getByEmail(String email);

    UserDTO getCurrentUser();

    UserDTO registrationAndSendOTP(UserDTO userDTO);

    UserDTO verifyAndActivateAccount(OtpVerificationDTO otpVerificationDTO);


}
