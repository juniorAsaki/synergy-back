package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.ReservationService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/reservations")
public class ReservationResource {

    private final ReservationService reservationService;

    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save Reservation")
    private ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservationDTO){
        log.debug("REST request to save reservation: {}", reservationDTO);
        return new ResponseEntity<>(reservationService.saveReservation(reservationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get reservation by id", description = "this endpoint allow to get reservation by id")
    public Optional<ReservationDTO> getReservationById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return reservationService.getById(id);

    }

    @GetMapping
    @Operation(summary = "get all reservations", description = "this endpoint allow to get all reservation")
    public List<ReservationDTO> getAllReservation(){
        log.debug("REST Request to all reservations ");
        return reservationService.getAll();
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get reservation by slug", description = "this endpoint allow to get reservation by slug")
    public ResponseEntity<?> getReservationBySlug(@PathVariable String slug){
        log.debug("REST Request to get one by slug {}", slug);
        Optional<ReservationDTO> reservationDTO = reservationService.getBySlug(slug);
        if (reservationDTO.isPresent()){
            return new ResponseEntity<>(reservationDTO.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Slug project not found",HttpStatus.NOT_FOUND);
        }
    }

}
