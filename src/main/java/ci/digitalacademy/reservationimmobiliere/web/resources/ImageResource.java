package ci.digitalacademy.reservationimmobiliere.web.resources;



import ci.digitalacademy.reservationimmobiliere.services.impl.ImageServiceImpl;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@AllArgsConstructor
@Slf4j
public class ImageResource {

    private final ImageServiceImpl imageService;

    @PostMapping
    public ResponseEntity<ImageDTO> addImage(@RequestBody ImageDTO imageDTO) {
        log.debug("request to add image {}", imageDTO);
        ImageDTO createdImage = imageService.addImage(imageDTO);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED); // Retourne un code 201 (Created)
    }

    @GetMapping("/residence/{residenceId}")
    public ResponseEntity<List<ImageDTO>> getImagesByResidence(@PathVariable Long residenceId) {
        log.debug("request to find image by residence {}", residenceId);
        List<ImageDTO> images = imageService.getImagesByResidence(residenceId);
        return ResponseEntity.ok(images); // Retourne un code 200 (OK)
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        log.debug("request to update image {}", imageDTO);
        ImageDTO updatedImage = imageService.updateImage(id, imageDTO);
        return ResponseEntity.ok(updatedImage); // Retourne un code 200 (OK)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        log.debug("request to delete image {}", id);
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build(); // Retourne un code 204 (No Content)
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ImageDTO> getImageBySlug(@PathVariable String slug) {
        log.debug("request to find image by slug {}", slug);
        ImageDTO imageDTO = imageService.getImageBySlug(slug);
        return ResponseEntity.ok(imageDTO); // Retourne un code 200 (OK)
    }
}
