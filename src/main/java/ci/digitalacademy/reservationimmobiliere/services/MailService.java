package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorVeservationDTO;
import jakarta.mail.MessagingException;

public interface MailService {


    void sendEmail(OtherCustomerDTO otherCustomerDTO, VisitorVeservationDTO visitorVeservationDTO) throws MessagingException;
}
