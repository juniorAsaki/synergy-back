package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtpVerificationDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserDTO userDTO) {
        log.debug("Rest request to register user {}", userDTO);
        userService.registrationAndSendOTP(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        log.debug("Rest request to find user with id {}", id);
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Rest request to delete user with id {}", id);
        userService.delete(id);
    }

    @PostMapping("/verify-and-activate")
    public ResponseEntity<UserDTO> verifyAndActivate(@RequestBody OtpVerificationDTO otpVerificationDTO) {
        UserDTO updatedUser = userService.verifyAndActivateAccount(otpVerificationDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
