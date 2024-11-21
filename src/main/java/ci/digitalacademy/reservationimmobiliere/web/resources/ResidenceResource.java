package ci.digitalacademy.reservationimmobiliere.web.resources;



import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/residences")
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ResidenceResource {

    private final ResidenceService residenceService;

    @PostMapping
    @ApiResponse(responseCode = "201", description= "Request to save residence")
    public ResponseEntity<ResidenceDTO> save(@RequestBody ResidenceDTO residenceDTO) {
        log.debug("request to create residence {}", residenceDTO);
        return new ResponseEntity<>(residenceService.saveResidence(residenceDTO), HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(summary = "get all residence", description = "this endpoint allow to get all residence")
    public List<ResidenceDTO> getAllResidences() {
        log.debug("request to get all residences");
        return residenceService.getAllResidences();
    }
    @GetMapping("/{id}")
    @Operation(summary = "get residence by id", description = "this endpoint allow to get residence by id")
    public ResponseEntity<?> getResidenceById(@PathVariable Long id) {
        log.debug("request to get residence {}", id);
        return new ResponseEntity<>(residenceService.getResidenceById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request to get residence"),
            @ApiResponse(responseCode = "404", description = "Residence not found", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "update residence", description = "this endpoint allow to update residence")
    public ResidenceDTO updateTeacher( @RequestBody ResidenceDTO residenceDTO, @PathVariable Long id) throws IOException {
        log.debug(" REST Request to update  {}", residenceDTO);
        return residenceService.updateResidence(residenceDTO, id);
    }
        @DeleteMapping("/{id}")
    @Operation(summary = "delete residence", description = "this endpoint allow to delete residence")
    public void deleteResidence(@PathVariable Long id) {
        log.debug("request to delete residence {}", id);
        residenceService.deleteResidence(id);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get residence by slug", description = "this endpoint allow to get residence by slug")
    public ResponseEntity<?> getResidenceById(@PathVariable String slug) {
        log.debug("request to get residence by slug {}", slug);
        return new ResponseEntity<>(residenceService.getResidenceBySlug(slug), HttpStatus.OK);
    }

}

