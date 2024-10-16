package ci.digitalacademy.reservationimmobiliere.services;


import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;

import java.util.List;
import java.util.Optional;

public interface OtherCustomerService {

    OtherCustomerDTO save(OtherCustomerDTO otherCustomerDTO);

    Optional<OtherCustomerDTO> getById(Long id);

    List<OtherCustomerDTO> getAll();

    Optional<OtherCustomerDTO> getBySlug(String slug);

    OtherCustomerDTO getByEmail(String email);

    void delete(Long id);

    OtherCustomerDTO saveCustomer(OtherCustomerDTO otherCustomerDTO);
}
