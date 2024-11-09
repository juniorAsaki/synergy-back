package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.EntityMapper;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ResidenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResidenceMapperImpl implements ResidenceMapper {

    private final ModelMapper modelMapper;
    @Override
    public ResidenceDTO fromEntity(Residence entity) {
        log.debug("Mapping Residence to ResidenceDTO");
        return modelMapper.map(entity, ResidenceDTO.class);
    }

    @Override
    public Residence toEntity(ResidenceDTO dto) {
        log.debug("Mapping ResidenceDTO to Residence");
        return modelMapper.map(dto, Residence.class);
    }
}
