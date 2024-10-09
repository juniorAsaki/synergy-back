package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Owner;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ResidenceMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResidenceMapperImpl implements ResidenceMapper {

    private final ModelMapper modelMapper;
    @Override
    public ResidenceDTO fromEntity(Residence entity) {
        return modelMapper.map(entity, ResidenceDTO.class);
    }

    @Override
    public Residence toEntity(ResidenceDTO dto) {
        return modelMapper.map(dto, Residence.class);
    }
}
