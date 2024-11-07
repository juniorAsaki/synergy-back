package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.UserRepository;
import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.services.EmailService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtpVerificationDTO;
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
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update user {}", userDTO);
        return getById(userDTO.getId()).map(user -> {
            user.setEmail(userDTO.getEmail());
            return save(user);
        }).orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id).map(user -> {
            return userMapper.fromEntity(user);
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getByEmail(String email) {
        log.debug("Request to get User by email : {}", email);
        return userRepository.findByEmail(email).map(user -> {
            return userMapper.fromEntity(user);
        }).orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public UserDTO getCurrentUser() {
        log.debug("Request to get current user");
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        String email = null;

        if (authentication == null) {
            email = null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            email = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            email = jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            email = s;
        }
        UserDTO userDTO = getByEmail(email);
        UserDTO user = new UserDTO();
        if (userDTO != null) {
            user = userDTO;
        }
        return user;
    }


    public UserDTO registrationAndSendOTP(UserDTO userDTO) {
        log.debug("Request to registration and send OTP for user {}", userDTO);
        String password = userDTO.getPassword();
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        int otp = generateOTP();
        LocalDateTime expirationDate = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        userDTO.setOtpCode(otp);
        userDTO.setOtpExpirationDate(expirationDate);
        UserDTO user = save(userDTO);
        emailService.sendOTPEmail(userDTO);
        return user;



    }
    private int generateOTP() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public UserDTO verifyAndActivateAccount(OtpVerificationDTO otpVerificationDTO) {
        User user = userRepository.findByEmail(otpVerificationDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        if (user.getOtpCode() != null
                && user.getOtpCode().equals(otpVerificationDTO.getOtpCode())
                && !isOtpExpired(user.getOtpExpirationDate())) {
            user.setVerified(true);
            user.setActivated(true);
            User savedUser = userRepository.save(user);
            return userMapper.fromEntity(savedUser);
        } else {
            throw new IllegalArgumentException("OTP non valide ou expiré.");
        }
    }

    private boolean isOtpExpired(LocalDateTime expirationDate) {
        return !expirationDate.isAfter(LocalDateTime.now());
    }

}



