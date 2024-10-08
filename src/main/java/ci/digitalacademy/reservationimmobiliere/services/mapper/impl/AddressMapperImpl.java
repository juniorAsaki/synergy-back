package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapperImpl implements AddressMapper {

    private final ModelMapper modelMapper;

    @Override
    public AddressDTO fromEntity(Address entity) {
        return modelMapper.map(entity, AddressDTO.class);
    }

    @Override
    public Address toEntity(AddressDTO dto) {
        return  modelMapper.map(dto, Address.class);
    }
}
