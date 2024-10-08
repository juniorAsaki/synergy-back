package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.models.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResidenceDTO {

    private Long id;
    private Double price;
    private String description;
    private boolean available;
    private String slug;

    private List<ReviewDTO> reviews;
    private AddressDTO address;
    private List<ImageDTO> images;
}
