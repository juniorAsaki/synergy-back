package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Reservation;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReservationDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationMapperImpl implements ReservationMapper {

    private final ModelMapper modelMapper;
    @Override
    public ReservationDTO fromEntity(Reservation entity) {
        log.debug("Mapping Reservation to ReservationDTO");
        return modelMapper.map(entity, ReservationDTO.class);
    }

    @Override
    public Reservation toEntity(ReservationDTO dto) {
        log.debug("Mapping ReservationDTO to Reservation");
        return modelMapper.map(dto, Reservation.class);
    }
}
