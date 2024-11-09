package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.AddressRepository;
import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.services.AddressService;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.AddressMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.fromEntity(address);
    }

    @Override
    public Optional<AddressDTO> findById(Long id) {
        log.debug("Request to get Address by id : {}", id);
        return addressRepository.findById(id).map(address -> {
            return addressMapper.fromEntity(address);
        });
    }

    @Override
    public Optional<AddressDTO> findBySlug(String slug) {
        log.debug("Request to get Customer by slug {}", slug);
        return addressRepository.findBySlug(slug).map(address -> {
            return addressMapper.fromEntity( address);
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);

    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        log.debug("Request to save Customer {}", addressDTO);
        final String slug = SlugifyUtils.generate(addressDTO.getCity());
        addressDTO.setSlug(slug);
        return save(addressDTO);
    }
}
