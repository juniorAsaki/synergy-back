package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        log.debug("Rest request to save user {}", userDTO);
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        log.debug("Rest request to find user with id {}", id);
        Optional<UserDTO> user = userService.getById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Rest request to delete user with id {}", id);
        userService.delete(id);
    }
}
