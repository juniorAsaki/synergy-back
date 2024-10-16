package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.services.MailService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorVeservationDTO;
import groovy.util.logging.Slf4j;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine springTemplateEngine;

    public void sendEmail(OtherCustomerDTO otherCustomerDTO, VisitorVeservationDTO visitorVeservationDTO) throws MessagingException {

        Context context = new Context();

        context.setVariable("to", otherCustomerDTO.getEmail());
        context.setVariable("checkInDate", visitorVeservationDTO.getStartDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")));
        context.setVariable("checkOutDate", visitorVeservationDTO.getEndDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")));
        context.setVariable("residenceName", visitorVeservationDTO.getResidence().getName());
        context.setVariable("totalPrice", visitorVeservationDTO.getTotalAmount().toString() + " F CFA");
        context.setVariable("clientName", otherCustomerDTO.getFirstName() + " " + otherCustomerDTO.getLastName());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        String content = springTemplateEngine.process("envoiEmail", context);

        message.setTo(otherCustomerDTO.getEmail());
        message.setFrom("angeseugban2000@gmail.com");
        message.setSubject("Confirmation de réservation - " + visitorVeservationDTO.getSlug());
        message.setText(content, true);

        javaMailSender.send(mimeMessage);
    }
}
