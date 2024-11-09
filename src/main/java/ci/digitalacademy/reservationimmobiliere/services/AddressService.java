package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;

import java.util.Optional;

public interface AddressService {

    AddressDTO save (AddressDTO address);

    Optional<AddressDTO> findById(Long id);

    Optional<AddressDTO> findBySlug(String slug);

    AddressDTO saveAddress(AddressDTO addressDTO);

    void delete (Long id);




}
