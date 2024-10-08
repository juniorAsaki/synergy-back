package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Owner;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerMapperImpl implements OwnerMapper {

    private final ModelMapper  modelMapper;
    @Override
    public OwnerDTO fromEntity(Owner entity) {
        return modelMapper.map(entity, OwnerDTO.class);
    }

    @Override
    public Owner toEntity(OwnerDTO dto) {
        return modelMapper.map(dto, Owner.class);
    }
}
