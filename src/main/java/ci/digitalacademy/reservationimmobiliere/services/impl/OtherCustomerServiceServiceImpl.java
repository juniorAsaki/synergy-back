package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.OtherCustomerRepository;
import ci.digitalacademy.reservationimmobiliere.models.OtherCustomer;
import ci.digitalacademy.reservationimmobiliere.services.OtherCustomerService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.OtherCustomerMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtherCustomerServiceServiceImpl implements OtherCustomerService {

    private final OtherCustomerRepository otherCustomerRepository;
    private final OtherCustomerMapper otherCustomerMapper;
    @Override
    public OtherCustomerDTO save(OtherCustomerDTO otherCustomerDTO) {
        log.debug("Request to save Customer {}", otherCustomerDTO);
        OtherCustomer otherCustomer = otherCustomerMapper.toEntity(otherCustomerDTO);
        otherCustomer = otherCustomerRepository.save(otherCustomer);
        return otherCustomerMapper.fromEntity(otherCustomer);
    }

    @Override
    public Optional<OtherCustomerDTO> getById(Long id) {
        log.debug("Request to get Customer by id {}", id);
        return otherCustomerRepository.findById(id).map(otherCustomer -> {
            return otherCustomerMapper.fromEntity(otherCustomer);
        });
    }

    @Override
    public List<OtherCustomerDTO> getAll() {
        log.debug("Request to get all Customer");
        return otherCustomerRepository.findAll().stream().map(otherCustomer -> {
            return otherCustomerMapper.fromEntity(otherCustomer);
        }).toList();
    }

    @Override
    public Optional<OtherCustomerDTO> getBySlug(String slug) {
        log.debug("Request to get Customer by slug {}", slug);
        return otherCustomerRepository.findBySlug(slug).map(otherCustomer -> {
            return otherCustomerMapper.fromEntity(otherCustomer);
        });
    }

    @Override
    public OtherCustomerDTO getByEmail(String email) {
        log.debug("Request to get Customer by email {}", email);
        return otherCustomerRepository.findByEmail(email).map(otherCustomer -> {
            return otherCustomerMapper.fromEntity(otherCustomer);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer by id {}", id);
        otherCustomerRepository.deleteById(id);
    }

    @Override
    public OtherCustomerDTO saveCustomer(OtherCustomerDTO otherCustomerDTO) {
        log.debug("Request to save Customer {}", otherCustomerDTO);
        final String slug = SlugifyUtils.generate(otherCustomerDTO.getFirstName());
        otherCustomerDTO.setSlug(slug);
        return save(otherCustomerDTO);
    }


}
