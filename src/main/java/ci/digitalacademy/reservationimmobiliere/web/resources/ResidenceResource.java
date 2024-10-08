package ci.digitalacademy.reservationimmobiliere.web.resources;



import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.impl.ResidenceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/residences")
@AllArgsConstructor
public class ResidenceResource {

    private final ResidenceServiceImpl residenceService;

    // Créer une nouvelle résidence
    @PostMapping
    public ResponseEntity<ResidenceDTO> createResidence(@RequestBody ResidenceDTO residenceDTO) throws IOException {
        ResidenceDTO createdResidence = residenceService.createResidence(residenceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResidence);
    }

    // Obtenir toutes les résidences
    @GetMapping
    public ResponseEntity<List<ResidenceDTO>> getAllResidences() {
        List<ResidenceDTO> residences = residenceService.getAllResidences();
        return ResponseEntity.ok(residences);
    }

    // Obtenir une résidence par ID
    @GetMapping("/{id}")
    public ResponseEntity<ResidenceDTO> getResidenceById(@PathVariable Long id) {
        ResidenceDTO residence = residenceService.getResidenceById(id);
        return ResponseEntity.ok(residence);
    }

    // Mettre à jour une résidence
    @PutMapping("/{id}")
    public ResponseEntity<ResidenceDTO> updateResidence(@PathVariable Long id, @RequestBody ResidenceDTO residenceDTO) throws IOException {
        ResidenceDTO updatedResidence = residenceService.updateResidence(id, residenceDTO);
        return ResponseEntity.ok(updatedResidence);
    }

    // Supprimer une résidence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResidence(@PathVariable Long id) {
        residenceService.deleteResidence(id);
        return ResponseEntity.noContent().build();
    }
}

