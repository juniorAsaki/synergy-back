package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddressMapperImpl implements AddressMapper {

    private final ModelMapper modelMapper;

    @Override
    public AddressDTO fromEntity(Address entity) {
        log.debug("Mapping Address to AddressDTO");
        return modelMapper.map(entity, AddressDTO.class);
    }

    @Override
    public Address toEntity(AddressDTO dto) {
        log.debug("Mapping AddressDTO to Address");
        return  modelMapper.map(dto, Address.class);
    }
}
