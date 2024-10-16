package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.VisitorReservation;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorVeservationDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.VisitorReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisitorReservationMapperImpl implements VisitorReservationMapper {

    private final ModelMapper modelMapper;
    @Override
    public VisitorVeservationDTO fromEntity(VisitorReservation entity) {
        log.debug("Mapping VisitorReservation to VisitorVeservationDTO");
        return modelMapper.map(entity, VisitorVeservationDTO.class);
    }

    @Override
    public VisitorReservation toEntity(VisitorVeservationDTO dto) {
        log.debug("Mapping VisitorVeservationDTO to VisitorReservation");
        return modelMapper.map(dto, VisitorReservation.class);
    }
}
