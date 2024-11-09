package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.services.OtherCustomerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
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
@RequestMapping("/api/v1/public/otherCustomers")
public class OtherCustomerResource {


    private final OtherCustomerService otherCustomerService;

    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save customer")
    private ResponseEntity<OtherCustomerDTO> saveCustomer(@RequestBody OtherCustomerDTO otherCustomerDTO){
        log.debug("REST request to save customer: {}", otherCustomerDTO);
        return new ResponseEntity<>(otherCustomerService.saveCustomer(otherCustomerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get customer by id", description = "this endpoint allow to get customer by id")
    public Optional<OtherCustomerDTO> getCustomerById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return otherCustomerService.getById(id);

    }

    @GetMapping
    @Operation(summary = "get all customer", description = "this endpoint allow to get all customer")
    public List<OtherCustomerDTO> getAllCustomer(){
        log.debug("REST Request to all customers ");
        return otherCustomerService.getAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete customer", description = "this endpoint allow to delete customer")
    public void deleteCustomer(@PathVariable Long id){
        log.debug("REST Request to delete {} ", id);
        otherCustomerService.delete(id);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get customer by slug", description = "this endpoint allow to get customer by slug")
    public ResponseEntity<?> getCustomerBySlug(@PathVariable String slug){
        log.debug("REST Request to get one by slug {}", slug);
        return new ResponseEntity<>(otherCustomerService.getBySlug(slug), HttpStatus.OK);
    }

}
