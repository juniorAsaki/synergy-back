package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.UserReservationService;
import ci.digitalacademy.reservationimmobiliere.services.dto.User_reservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/customer/user-reservations")
public class UserReservationResource {

    private final UserReservationService userReservationService;

    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save Reservation")
    private ResponseEntity<User_reservationDTO> saveReservation(@RequestBody User_reservationDTO userReservationDTO){
        log.debug("REST request to save reservation: {}", userReservationDTO);
        return new ResponseEntity<>(userReservationService.saveReservation(userReservationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get reservation by id", description = "this endpoint allow to get reservation by id")
    public Optional<User_reservationDTO> getReservationById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return userReservationService.getById(id);

    }

    @GetMapping
    @Operation(summary = "get all reservations", description = "this endpoint allow to get all reservation")
    public List<User_reservationDTO> getAllReservation(){
        log.debug("REST Request to all reservations ");
        return userReservationService.getAll();
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get reservation by slug", description = "this endpoint allow to get reservation by slug")
    public ResponseEntity<?> getReservationBySlug(@PathVariable String slug){
        log.debug("REST Request to get reservation by slug {}", slug);
        return new ResponseEntity<>(userReservationService.getBySlug(slug), HttpStatus.OK);
    }

    @GetMapping("/isAvailable")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long residenceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        boolean isAvailable = userReservationService.isPropertyAvailable(residenceId, startDate, endDate);
        return ResponseEntity.ok(isAvailable);
    }
}
