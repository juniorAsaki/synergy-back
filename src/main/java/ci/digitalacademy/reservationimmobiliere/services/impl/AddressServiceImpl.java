package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.AddressRepository;
import ci.digitalacademy.reservationimmobiliere.services.AddressService;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.AddressMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        addressDTO.setSlug(SlugifyUtils.generate(addressDTO.getCity()));
        return addressMapper.fromEntity(addressRepository.save(addressMapper.toEntity(addressDTO)) );
    }

    @Override
    public Optional<AddressDTO> findById(Long id) {
        return addressRepository.findById(id).map(address -> {
            return  addressMapper.fromEntity(address);
        });
    }

    @Override
    public Optional<AddressDTO> findBySlug(String Slug) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);

    }
}
