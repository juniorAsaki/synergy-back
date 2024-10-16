package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO);

    Optional<CustomerDTO> getById(Long id);

    List<CustomerDTO> getAll();

    Optional<CustomerDTO> getBySlug(String slug);

    void delete(Long id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> findByUserId(Long id);
}
