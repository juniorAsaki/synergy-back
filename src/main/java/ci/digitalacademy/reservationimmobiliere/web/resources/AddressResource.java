package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.AddressService;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/addresses")
// This class represents the RESTful API endpoints for managing addresses.
public class AddressResource {

    private final AddressService addressService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Request to save Address")
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO){
        log.debug("REST request to save Address:{}", addressDTO);
        return new ResponseEntity<>(addressService.save(addressDTO),  HttpStatus.CREATED);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> findOneBySlug(@PathVariable String slug){
        log.debug("REST request to find by slug: {}", slug);
        return new ResponseEntity<>(addressService.findBySlug(slug), HttpStatus.OK);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findOneById(@PathVariable Long id){
        log.debug("REST request to find by id: {}", id);
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);

    }
    @DeleteMapping("/id")
    public void deleteById(@PathVariable Long id){
        log.debug("REST request to delete by id:{}", id);
        addressService.delete(id);
    }





}
