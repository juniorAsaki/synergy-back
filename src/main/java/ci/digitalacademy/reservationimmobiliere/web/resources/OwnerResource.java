package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.AddressService;
import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.PictureResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.impl.PictureResidenceServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/owners")
@Slf4j
@RequiredArgsConstructor
public class OwnerResource {

    private final OwnerService ownerService;
    private final ResidenceService residenceService;
    private final PictureResidenceServiceImpl imageService;
    private final AddressService addressService;

    @GetMapping("/{id}")
    @Operation(summary = "get owner by id", description = "this endpoint allow to get owner by id")
    public ResponseEntity<?> getOwnerById(@PathVariable Long id){
        log.debug(" REST Request to get one {}", id);
        return new ResponseEntity<>(ownerService.getById(id), HttpStatus.OK);
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
        return new ResponseEntity<>(ownerService.getBySlug(slug), HttpStatus.OK);
    }


    @PostMapping("/residences")
    @ApiResponse(responseCode = "201", description= "Request to save residence")
    public ResponseEntity<ResidenceDTO> save(@RequestBody ResidenceDTO residenceDTO) {
        log.debug("request to create residence {}", residenceDTO);
        return new ResponseEntity<>(residenceService.saveResidence(residenceDTO), HttpStatus.CREATED);
    }
    @GetMapping("/residences/{id}")
    @Operation(summary = "get all residence", description = "this endpoint allow to get all residence")
    public List<ResidenceDTO> getAllResidences(@PathVariable Long id) {
        log.debug("request to get all residences");
        return residenceService.findAllByOwner_Id(id);
    }
    @GetMapping("/residence/{id}")
    @Operation(summary = "get residence by id", description = "this endpoint allow to get residence by id")
    public ResponseEntity<?> getResidenceById(@PathVariable Long id) {
        log.debug("request to get residence {}", id);
        return new ResponseEntity<>(residenceService.getResidenceById(id), HttpStatus.OK);
    }
    @PutMapping("/residences/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request to get residence"),
            @ApiResponse(responseCode = "404", description = "Residence not found", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "update residence", description = "this endpoint allow to update residence")
    public ResidenceDTO updateTeacher( @RequestBody ResidenceDTO residenceDTO, @PathVariable Long id) throws IOException {
        log.debug(" REST Request to update  {}", residenceDTO);
        return residenceService.updateResidence(residenceDTO, id);
    }
    @DeleteMapping("/residences/{id}")
    @Operation(summary = "delete residence", description = "this endpoint allow to delete residence")
    public void deleteResidence(@PathVariable Long id) {
        log.debug("request to delete residence {}", id);
        residenceService.deleteResidence(id);
    }

    @GetMapping("/residences/slug/{slug}")
    @Operation(summary = "get residence by slug", description = "this endpoint allow to get residence by slug")
    public ResponseEntity<?> getResidenceById(@PathVariable String slug) {
        log.debug("request to get residence by slug {}", slug);
        return new ResponseEntity<>(residenceService.getResidenceBySlug(slug), HttpStatus.OK);
    }


    @PostMapping("/picture-residences/{id}")
    @ApiResponse(responseCode = "201", description= "Request to save image")
    public void addPicture(@RequestParam List<MultipartFile> pictures, @PathVariable Long id) {
        log.debug("request to add image {}", pictures);
        imageService.saveAllPictureResidence(pictures, id);
    }

    @DeleteMapping("/images/{id}")
    @Operation(summary = "delete image", description = "this endpoint allow to delete image")
    public void deleteImage(@PathVariable Long id) {
        log.debug("request to delete image {}", id);
        imageService.deleteImage(id);
    }

    @GetMapping("/images/{id}")
    @Operation(summary = "get image by id", description = "this endpoint allow to get image by id")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        log.debug("request to find image by id {}", id);
        return new ResponseEntity<>(imageService.getImageById(id), HttpStatus.OK);
    }

    @GetMapping("/images")
    @Operation(summary = "get all image", description = "this endpoint allow to get all image")
    public List<PictureResidenceDTO> getAllImage() {
        log.debug("request to get all image");
        return imageService.getAllImages();
    }

    @GetMapping("/images/slug/{slug}")
    @Operation(summary = "get image by slug", description = "this endpoint allow to get image by slug")
    public ResponseEntity<?> getImageBySlug(@PathVariable String slug) {
        log.debug("request to find image by slug {}", slug);
        return new ResponseEntity<>(imageService.getImageBySlug(slug), HttpStatus.OK);
    }


    @PostMapping("/addresses")
    @ApiResponse(responseCode = "201", description = "Request to save Address")
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO){
        log.debug("REST request to save Address:{}", addressDTO);
        return new ResponseEntity<>(addressService.saveAddress(addressDTO),  HttpStatus.CREATED);
    }

    @GetMapping("/addresses/slug/{slug}")
    @Operation(summary = "get address by slug", description = "this endpoint allow to get address by slug")
    public ResponseEntity<?> findOneBySlug(@PathVariable String slug){
        log.debug("REST request to find by slug: {}", slug);
        return new ResponseEntity<>(addressService.findBySlug(slug), HttpStatus.OK);

    }
    @GetMapping("/addresses/{id}")
    @Operation(summary = "get address by id", description = "this endpoint allow to get address by id")
    public ResponseEntity<?> findOneById(@PathVariable Long id){
        log.debug("REST request to find by id: {}", id);
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);

    }
    @DeleteMapping("/addresses/{id}")
    @Operation(summary = "delete address", description = "this endpoint allow to delete address")
    public void deleteById(@PathVariable Long id){
        log.debug("REST request to delete by id:{}", id);
        addressService.delete(id);
    }
}
