package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ResidenceService {


    ResidenceDTO save(ResidenceDTO residenceDTO) ;
    List<ResidenceDTO> getAllResidences();
    Optional <ResidenceDTO> getResidenceById(Long id);
    ResidenceDTO updateResidence(ResidenceDTO residenceDTO, Long id ) throws IOException;
    void deleteResidence(Long id);
    Optional <ResidenceDTO> getResidenceBySlug(String slug);
    ResidenceDTO update(ResidenceDTO residenceDTO);

    ResidenceDTO saveResidence(ResidenceDTO residenceDTO);
}
