package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.RegistrationUserAndOwnerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/owners")
@Slf4j
@RequiredArgsConstructor
public class OwnerResource {

    private final OwnerService ownerService;

    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save owner")
    private ResponseEntity<RegistrationUserAndOwnerDTO> saveOwner(@RequestBody RegistrationUserAndOwnerDTO registrationUserAndOwnerDTO){
        log.debug("REST request to save owner: {}", registrationUserAndOwnerDTO);
        return new ResponseEntity<>(ownerService.saveOwnerAndUser(registrationUserAndOwnerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get owner by id", description = "this endpoint allow to get owner by id")
    public Optional<OwnerDTO> getOwnerById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return ownerService.getById(id);

    }

    @GetMapping
    @Operation(summary = "get all owner", description = "this endpoint allow to get all owner")
    public List<OwnerDTO> getAllOwner(){
        log.debug("REST Request to all owner ");
        return ownerService.getAll();
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request to get owner"),
            @ApiResponse(responseCode = "404", description = "Owner not found", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerDTO, @PathVariable Long id) {
        log.debug("REST request to update Owner {} {}", ownerDTO, id);
        OwnerDTO updateOwner = ownerService.update(ownerDTO, id);
        return new ResponseEntity<>(updateOwner, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete owner", description = "this endpoint allow to delete owner")
    public void deleteOwner(@PathVariable Long id){
        log.debug("REST Request to delete {} ", id);
        ownerService.delete(id);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get owner by slug", description = "this endpoint allow to get owner by slug")
    public ResponseEntity<?> getOwnerBySlug(@PathVariable String slug){
        log.debug("REST Request to get one by slug {}", slug);
        Optional<OwnerDTO> ownerDTO = ownerService.getBySlug(slug);
        if (ownerDTO.isPresent()){
            return new ResponseEntity<>(ownerDTO.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Slug project not found",HttpStatus.NOT_FOUND);
        }
    }
}
