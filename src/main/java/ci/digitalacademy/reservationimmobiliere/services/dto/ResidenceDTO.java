package ci.digitalacademy.reservationimmobiliere.services.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ResidenceDTO {

    private Long id;
    private BigDecimal price;
    private String description;
    private String name;
    private Integer NumberOfRooms;
    private Integer NumberOfShowers;
    private Integer NumberOfDiningRoom;
    private Integer NumberOfTerrace;
    private Integer NumberOfLounges;
    private boolean wifi;
    private boolean parking;
    private boolean catering;
    private boolean cleaning;
    private boolean available;
    private String slug;


    private OwnerDTO owner;
    private AddressDTO address;
    private List<PictureResidenceDTO> images;
    private List<MultipartFile> imageFiles;
}
