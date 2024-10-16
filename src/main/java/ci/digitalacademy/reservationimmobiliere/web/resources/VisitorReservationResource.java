package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.services.VisitorReservationService;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorVeservationDTO;
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
@RequestMapping("/api/v1/public/visitor-reservations")
public class VisitorReservationResource {

    private final VisitorReservationService visitorReservationService;

    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save Reservation")
    private ResponseEntity<VisitorVeservationDTO> saveReservation(@RequestBody VisitorVeservationDTO visitorReservationDTO){
        log.debug("REST request to save reservation: {}", visitorReservationDTO);
        return new ResponseEntity<>(visitorReservationService.saveReservation(visitorReservationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get reservation by id", description = "this endpoint allow to get reservation by id")
    public Optional<VisitorVeservationDTO> getReservationById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return visitorReservationService.getById(id);

    }

    @GetMapping
    @Operation(summary = "get all reservations", description = "this endpoint allow to get all reservation")
    public List<VisitorVeservationDTO> getAllReservation(){
        log.debug("REST Request to all reservations ");
        return visitorReservationService.getAll();
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get reservation by slug", description = "this endpoint allow to get reservation by slug")
    public ResponseEntity<?> getReservationBySlug(@PathVariable String slug){
        log.debug("REST Request to get reservation by slug {}", slug);
        return new ResponseEntity<>(visitorReservationService.getBySlug(slug), HttpStatus.OK);
    }

    @GetMapping("/isAvailable")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long residenceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        boolean isAvailable = visitorReservationService.isPropertyAvailable(residenceId, startDate, endDate);
        return ResponseEntity.ok(isAvailable);
    }
}
