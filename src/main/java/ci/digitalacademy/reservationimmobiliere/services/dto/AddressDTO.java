package ci.digitalacademy.reservationimmobiliere.services.dto;

import ci.digitalacademy.reservationimmobiliere.models.Residence;

import java.util.Set;

public class AddressDTO {

    private Long id;
    private String city;

    private Set<ResidenceDTO> residences;

    private String Slug;
}
