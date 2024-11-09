package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtpVerificationDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.PasswordResetRequestDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

    @PostMapping(value = "/request-password-reset" , produces = "application/json")
    public ResponseEntity<Response> requestPasswordReset(@Valid @RequestBody PasswordResetRequestDTO request) {
        log.debug("Rest request to request password reset" , request);

        UserDTO user = userService.getByEmail(request.getEmail());
        if (user == null) {
            // Créer une réponse avec un message JSON
            Response response = new Response("Aucun utilisateur trouvé avec cet e-mail.");
            return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
        }

        userService.requestPasswordReset(user.getEmail());

        // Créer une réponse avec un message JSON
        Response response = new Response("Un code OTP a été envoyé par e-mail.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@Valid @RequestBody PasswordResetRequestDTO request) {
        log.debug("Rest request to reset password {}", request);

        if(request.getOtpCode() != null) {
            userService.resetPassword(request);
        }

        // Créer une réponse avec un message JSON
        Response response = new Response("Mot de passe réinitialisé avec succès.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Classe interne pour définir la structure de la réponse JSON
    @Getter
    @Setter
    public static class Response {

        @JsonProperty("message")
        private String message;

        public Response(String message) {
            this.message = message;
        }
    }

}
