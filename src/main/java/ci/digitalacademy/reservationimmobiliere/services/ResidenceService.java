package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ResidenceService {

    Optional<ResidenceDTO> getById(Long id);

    ResidenceDTO createResidence(ResidenceDTO residenceDTO) throws IOException;
    List<ResidenceDTO> getAllResidences();
    ResidenceDTO getResidenceById(Long id);
    ResidenceDTO updateResidence(Long id, ResidenceDTO residenceDTO) throws IOException;
    void deleteResidence(Long id);
    ResidenceDTO getResidenceBySlug(String slug);
}
