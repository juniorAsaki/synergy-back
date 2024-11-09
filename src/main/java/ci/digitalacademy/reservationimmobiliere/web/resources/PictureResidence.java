package ci.digitalacademy.reservationimmobiliere.web.resources;



import ci.digitalacademy.reservationimmobiliere.services.dto.PictureResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.impl.PictureResidenceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner/picture-residences")
@AllArgsConstructor
@Slf4j
public class PictureResidence {

    private final PictureResidenceServiceImpl imageService;

    @PostMapping("/residence/{id}/all")
    @ApiResponse(responseCode = "201", description= "Request to save image")
    public void addPicture(@RequestParam List<MultipartFile> pictures, @PathVariable Long id) {
        log.debug("request to add image {}", pictures);
        imageService.saveAllPictureResidence(pictures, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete image", description = "this endpoint allow to delete image")
    public void deleteImage(@PathVariable Long id) {
        log.debug("request to delete image {}", id);
        imageService.deleteImage(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get image by id", description = "this endpoint allow to get image by id")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        log.debug("request to find image by id {}", id);
        return new ResponseEntity<>(imageService.getImageById(id), HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get image by slug", description = "this endpoint allow to get image by slug")
    public ResponseEntity<?> getImageBySlug(@PathVariable String slug) {
        log.debug("request to find image by slug {}", slug);
        return new ResponseEntity<>(imageService.getImageBySlug(slug), HttpStatus.OK);
    }
}
