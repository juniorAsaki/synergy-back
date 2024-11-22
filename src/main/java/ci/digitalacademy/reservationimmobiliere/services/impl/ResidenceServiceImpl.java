package ci.digitalacademy.reservationimmobiliere.services.impl;



import ci.digitalacademy.reservationimmobiliere.Repository.ResidenceRepository;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.*;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ResidenceMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ResidenceServiceImpl implements ResidenceService {

    private final ResidenceRepository residenceRepository;
    private final ResidenceMapper residenceMapper;
    private final OwnerService ownerService;
    private final UserService userService;


    public ResidenceDTO save(ResidenceDTO residenceDTO)  {
        log.debug("Request to save Residence : {}", residenceDTO);
        Residence residence = residenceMapper.toEntity(residenceDTO);
        residence = residenceRepository.save(residence);
        return residenceMapper.fromEntity(residence);
    }


    public List<ResidenceDTO> getAllResidences() {
        log.debug("Request to get all residences");
        return residenceRepository.findAll().stream().map(residence -> {
            return residenceMapper.fromEntity(residence);
        }).toList();
    }

    public Optional <ResidenceDTO> getResidenceById(Long id) {
        log.debug("Request to get residence by id: {}", id);
        return residenceRepository.findById(id).map(residence ->{
        return residenceMapper.fromEntity(residence);
        });
    }

    public Optional <ResidenceDTO> getResidenceBySlug(String slug) {
        log.debug("Request to get residence by slug: {}", slug);
        return residenceRepository.findBySlug(slug).map(residence -> {
            return residenceMapper.fromEntity(residence);
        });
    }

    @Override
    public ResidenceDTO update(ResidenceDTO residenceDTO) {
        log.debug("Request to update residence: {}", residenceDTO);
        return getResidenceById(residenceDTO.getId()).map(residence -> {
            residence.setDescription(residenceDTO.getDescription());
            residence.setPrice(residenceDTO.getPrice());
            return save(residenceDTO);
        }).orElseThrow(()-> new IllegalArgumentException());
    }

    @Override
    public ResidenceDTO updateResidence(ResidenceDTO residenceDTO, Long id) {
        log.debug("Request to update residence by id: {}", id);
        residenceDTO.setId(id);
        return update(residenceDTO);
    }
    @Override
    public ResidenceDTO saveResidence(ResidenceDTO residenceDTO) {
        log.debug("Request to save residence : {}", residenceDTO);
        UserDTO user = userService.getCurrentUser();
        Optional<OwnerDTO> ownerDTO = ownerService.getById(user.getId());
        if (ownerDTO.isPresent()) {
            residenceDTO.setOwner(ownerDTO.get());
        }
        final String slug = SlugifyUtils.generate(residenceDTO.getDescription());
        residenceDTO.setSlug(slug);
        return save(residenceDTO);
    }

    @Override
    public List<ResidenceDTO> searchResidences(SearchDTO search) {
        log.debug("Request to search residences : {}", search);
        List<Residence> residences = residenceRepository.findByPriceOrAddress_CityIgnoreCaseOrAddress_DistrictIgnoreCaseOrNameIgnoreCase(search.getPrice(), search.getCity(), search.getDistrict(), search.getName());
        return residences.stream().map(residence -> {
            return residenceMapper.fromEntity(residence);
        }).toList();
    }

    @Override
    public List<ResidenceDTO> findAllByOwner_IdPerson(Long idPerson) {
        return this.residenceRepository.findAllByOwner_IdPerson(idPerson).stream().map(residenceMapper::fromEntity).collect(Collectors.toList());
    }


    public void deleteResidence(Long id) {
        log.debug("Request to delete residence by id: {}", id);
        residenceRepository.deleteById(id);
    }
}



