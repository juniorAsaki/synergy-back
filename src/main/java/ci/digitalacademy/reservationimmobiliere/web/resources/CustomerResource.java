package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
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
@RequestMapping("/api/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;


    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save customer")
    private ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        log.debug("REST request to save customer: {}", customerDTO);
        return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get customer by id", description = "this endpoint allow to get customer by id")
    public Optional<CustomerDTO> getCustomerById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return customerService.getById(id);

    }

    @GetMapping
    @Operation(summary = "get all customer", description = "this endpoint allow to get all customer")
    public List<CustomerDTO> getAllCustomer(){
        log.debug("REST Request to all customers ");
        return customerService.getAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete customer", description = "this endpoint allow to delete customer")
    public void deleteCustomer(@PathVariable Long id){
        log.debug("REST Request to delete {} ", id);
        customerService.delete(id);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get customer by slug", description = "this endpoint allow to get customer by slug")
    public ResponseEntity<?> getCustomerBySlug(@PathVariable String slug){
        log.debug("REST Request to get one by slug {}", slug);
        Optional<CustomerDTO> customerDTO = customerService.getBySlug(slug);
        if (customerDTO.isPresent()){
            return new ResponseEntity<>(customerDTO.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Slug project not found",HttpStatus.NOT_FOUND);
        }
    }
}
