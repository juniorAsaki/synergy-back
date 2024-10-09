package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.models.Review;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ResidenceDTO {

    private Long id; // Identifiant de la résidence
    private Double price; // Prix de la résidence
    private String description; // Description de la résidence
    private boolean available; // Disponibilité
    private String slug; // Slug pour URL


    private AddressDTO address; // Adresse de la résidence
    private List<ImageDTO> images; // Images de la résidence
    private List<MultipartFile> imageFiles; // Fichiers d'images à uploader
}
