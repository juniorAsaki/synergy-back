package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.CustomerRepository;
import ci.digitalacademy.reservationimmobiliere.models.Customer;
import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.CustomerMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.fromEntity(customer);
    }

    @Override
    public Optional<CustomerDTO> getById(Long id) {
        log.debug("Request to get Customer by id {}", id);
        return customerRepository.findById(id).map(customer -> {
            return customerMapper.fromEntity(customer);
        });
    }

    @Override
    public List<CustomerDTO> getAll() {
        log.debug("Request to get all Customer");
        return customerRepository.findAll().stream().map(customer -> {
            return customerMapper.fromEntity(customer);
        }).toList();
    }

    @Override
    public Optional<CustomerDTO> getBySlug(String slug) {
        log.debug("Request to get Customer by slug {}", slug);
        return customerRepository.findBySlug(slug).map(customer -> {
            return customerMapper.fromEntity(customer);
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer by id {}", id);
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.debug("Request to save Customer {}", customerDTO);
        final String slug = SlugifyUtils.generate(customerDTO.getEmail());
        customerDTO.setSlug(slug);
        return save(customerDTO);
    }
}
