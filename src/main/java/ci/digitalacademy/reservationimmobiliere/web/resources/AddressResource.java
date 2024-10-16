package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.AddressService;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressResource {

    private final AddressService addressService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Request to save Address")
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO){
        log.debug("REST request to save Address:{}", addressDTO);
        return new ResponseEntity<>(addressService.saveAddress(addressDTO),  HttpStatus.CREATED);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get address by slug", description = "this endpoint allow to get address by slug")
    public ResponseEntity<?> findOneBySlug(@PathVariable String slug){
        log.debug("REST request to find by slug: {}", slug);
        return new ResponseEntity<>(addressService.findBySlug(slug), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    @Operation(summary = "get address by id", description = "this endpoint allow to get address by id")
    public ResponseEntity<?> findOneById(@PathVariable Long id){
        log.debug("REST request to find by id: {}", id);
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete address", description = "this endpoint allow to delete address")
    public void deleteById(@PathVariable Long id){
        log.debug("REST request to delete by id:{}", id);
        addressService.delete(id);
    }

}
