package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.VisitorReservation;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorReservationDTO;
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
    public VisitorReservationDTO fromEntity(VisitorReservation entity) {
        log.debug("Mapping VisitorReservation to VisitorReservationDTO");
        return modelMapper.map(entity, VisitorReservationDTO.class);
    }

    @Override
    public VisitorReservation toEntity(VisitorReservationDTO dto) {
        log.debug("Mapping VisitorReservationDTO to VisitorReservation");
        return modelMapper.map(dto, VisitorReservation.class);
    }
}
