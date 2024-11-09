package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.UserRepository;
import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.services.EmailService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtpVerificationDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.PasswordResetRequestDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save user {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.fromEntity(user);
    }



    @Override
    public Optional<UserDTO> getById(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id).map(userMapper::fromEntity);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getByEmail(String email) {
        log.debug("Request to get User by email : {}", email);
        return userRepository.findByEmail(email)
                .map(userMapper::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Aucun utilisateur trouvé avec l'e-mail : " + email));
    }

    @Override
    public UserDTO getCurrentUser() {
        log.debug("Request to get current user");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else if (principal instanceof Jwt) {
                email = ((Jwt) principal).getSubject();
            } else if (principal instanceof String) {
                email = (String) principal;
            }
        }

        return email != null ? getByEmail(email) : null; // Renvoie null si aucun email
    }

    public UserDTO registrationAndSendOTP(UserDTO userDTO) {
        log.debug("Request to registration and send OTP for user {}", userDTO);
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        int otp = generateOTP();
        LocalDateTime expirationDate = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        userDTO.setOtpCode(otp);
        userDTO.setOtpExpirationDate(expirationDate);
        UserDTO user = save(userDTO);
        emailService.sendActivationEmail(userDTO);
        return user;
    }

    private int generateOTP() {
        return 100000 + new Random().nextInt(900000);
    }

    public UserDTO verifyAndActivateAccount(OtpVerificationDTO otpVerificationDTO) {
        log.debug("Request to verify and activate account for user {}", otpVerificationDTO);
        UserDTO userDTO = getByEmail(otpVerificationDTO.getEmail());
        if (userDTO.getOtpCode() != null
                && userDTO.getOtpCode().equals(otpVerificationDTO.getOtpCode())
                && !isOtpExpired(userDTO.getOtpExpirationDate())) {
            userDTO.setVerified(true);
            userDTO.setActivated(true);
            return save(userDTO);
        } else {
            throw new IllegalArgumentException("OTP non valide ou expiré.");
        }
    }

    private boolean isOtpExpired(LocalDateTime expirationDate) {
        return !expirationDate.isAfter(LocalDateTime.now());
    }

    public void requestPasswordReset(String email) {
        log.debug("Request to reset password for email {}", email);
        UserDTO userDTO = getByEmail(email);
        int otp = generateOTP();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10);
        userDTO.setOtpCode(otp);
        userDTO.setOtpExpirationDate(expirationDate);
        save(userDTO);
        emailService.sendPasswordResetEmail(userDTO);
    }

    public void resetPassword(PasswordResetRequestDTO request) {
        log.debug("Request to reset password for email {}", request);
        UserDTO userDTO = getByEmail(request.getEmail());
        if (userDTO == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé avec cet e-mail.");
        }
        if (userDTO.getOtpCode() == null || !userDTO.getOtpCode().equals(request.getOtpCode())) {
            throw new IllegalArgumentException("OTP invalide ou non fourni.");
        }
        if (userDTO.getOtpExpirationDate() == null || userDTO.getOtpExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP expiré.");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        }
        userDTO.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        save(userDTO);
    }
}



