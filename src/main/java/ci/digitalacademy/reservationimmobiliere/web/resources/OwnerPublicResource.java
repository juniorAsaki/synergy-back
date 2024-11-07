package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
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
@RequestMapping("/api/v1/public/owners")
@Slf4j
@RequiredArgsConstructor
public class OwnerPublicResource {

    private final OwnerService ownerService;

    @PostMapping("/register")
    @ApiResponse(responseCode = "201", description= "Request to save owner")
    public void saveOwner(@RequestBody OwnerDTO ownerDTO){
        log.debug("REST request to save owner: {}", ownerDTO);
        ownerService.saveOwner(ownerDTO);
    }
}
