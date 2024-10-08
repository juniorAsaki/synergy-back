package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;

    private ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        log.debug("REST request to save customer: {}", customerDTO);
        return new ResponseEntity<>(customerService.saveCustomer(customerDTO), HttpStatus.CREATED);
    }
}
