package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorReservationDTO;


public interface EmailService {

    void sendPasswordResetEmail(UserDTO userDTO);

    void sendActivationEmail(UserDTO userDTO);

    void sendEmail(OtherCustomerDTO otherCustomerDTO, VisitorReservationDTO visitorReservationDTO) ;
}
