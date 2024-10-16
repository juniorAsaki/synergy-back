package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Owner;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.OwnerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OwnerMapperImpl implements OwnerMapper {

    private final ModelMapper  modelMapper;
    @Override
    public OwnerDTO fromEntity(Owner entity) {
        log.debug("Mapping Owner to OwnerDTO");
        return modelMapper.map(entity, OwnerDTO.class);
    }

    @Override
    public Owner toEntity(OwnerDTO dto) {
        log.debug("Mapping OwnerDTO to Owner");
        return modelMapper.map(dto, Owner.class);
    }
}
