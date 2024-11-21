package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.SearchDTO;

import java.io.IOException;
import java.math.BigDecimal;
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

    List<ResidenceDTO> searchResidences(SearchDTO search);
}
