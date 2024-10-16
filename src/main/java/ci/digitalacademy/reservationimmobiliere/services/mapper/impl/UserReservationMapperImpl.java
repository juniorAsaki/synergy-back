package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.UserReservation;
import ci.digitalacademy.reservationimmobiliere.services.dto.User_reservationDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.UserReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserReservationMapperImpl implements UserReservationMapper {
    private final ModelMapper modelMapper;
    @Override
    public User_reservationDTO fromEntity(UserReservation entity) {
        log.debug("Mapping UserReservation to User_reservationDTO");
        return modelMapper.map(entity, User_reservationDTO.class);
    }

    @Override
    public UserReservation toEntity(User_reservationDTO dto) {
        log.debug("Mapping User_reservationDTO to UserReservation");
        return modelMapper.map(dto, UserReservation.class);
    }
}
