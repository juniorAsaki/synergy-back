package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.RegistrationUserAndOwnerDTO;

import java.util.List;
import java.util.Optional;

public interface OwnerService {

    RegistrationUserAndOwnerDTO saveOwnerAndUser(RegistrationUserAndOwnerDTO registrationUserAndOwnerDTO);

    OwnerDTO save(OwnerDTO ownerDTO);
    Optional<OwnerDTO> getById(Long id);

    List<OwnerDTO> getAll();

    Optional<OwnerDTO> getBySlug(String slug);

    OwnerDTO update(OwnerDTO ownerDTO);

    OwnerDTO update(OwnerDTO ownerDTO, Long id);

    void delete(Long id);

}
