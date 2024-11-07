package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorReservationDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendOTPEmail(UserDTO userDTO);

    void sendEmail(OtherCustomerDTO otherCustomerDTO, VisitorReservationDTO visitorReservationDTO) ;
}
