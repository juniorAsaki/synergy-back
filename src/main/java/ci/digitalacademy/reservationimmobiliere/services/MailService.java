package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorReservationDTO;
import jakarta.mail.MessagingException;

public interface MailService {


    void sendEmail(OtherCustomerDTO otherCustomerDTO, VisitorReservationDTO visitorReservationDTO) throws MessagingException;
}
