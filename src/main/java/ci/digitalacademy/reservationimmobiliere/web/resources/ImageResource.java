package ci.digitalacademy.reservationimmobiliere.web.resources;



import ci.digitalacademy.reservationimmobiliere.services.impl.ImageServiceImpl;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@AllArgsConstructor
public class ImageResource {

    private final ImageServiceImpl imageService;

    @PostMapping
    public ResponseEntity<ImageDTO> addImage(@RequestBody ImageDTO imageDTO) {
        ImageDTO createdImage = imageService.addImage(imageDTO);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED); // Retourne un code 201 (Created)
    }

    @GetMapping("/residence/{residenceId}")
    public ResponseEntity<List<ImageDTO>> getImagesByResidence(@PathVariable Long residenceId) {
        List<ImageDTO> images = imageService.getImagesByResidence(residenceId);
        return ResponseEntity.ok(images); // Retourne un code 200 (OK)
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        ImageDTO updatedImage = imageService.updateImage(id, imageDTO);
        return ResponseEntity.ok(updatedImage); // Retourne un code 200 (OK)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build(); // Retourne un code 204 (No Content)
    }
}
