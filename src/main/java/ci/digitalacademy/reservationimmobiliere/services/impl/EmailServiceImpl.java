package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.services.EmailService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorReservationDTO;
import groovy.util.logging.Slf4j;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final SpringTemplateEngine templateEngine;

    private final JavaMailSender mailSender;


    @Override
    public void sendOTPEmail(UserDTO userDTO) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            message.setFrom("angeseugban2000@gmail.com");
            helper.setTo(userDTO.getEmail());
            helper.setSubject("Activation de votre compte");

            Context context = new Context();
            context.setVariable("email", userDTO.getEmail());
            context.setVariable("otp", userDTO.getOtpCode());
            context.setVariable("otpExpirationDate", userDTO.getOtpExpirationDate());

            String body = templateEngine.process("otp-email", context);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendEmail(OtherCustomerDTO otherCustomerDTO, VisitorReservationDTO visitorReservationDTO) {

    }
}
