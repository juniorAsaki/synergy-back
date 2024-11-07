package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerPublicResource {

    private final CustomerService customerService;

    @PostMapping("/register")
    @ApiResponse(responseCode = "201", description= "Request to save customer")
    private void saveCustomer(@RequestBody CustomerDTO customerDTO){
        log.debug("REST request to save customer: {}", customerDTO);
        customerService.saveCustomer(customerDTO);
    }
}
