package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Role;
import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class RoleMapperImpl implements RoleMapper {

    private final ModelMapper modelMapper;
    @Override
    public RoleDTO fromEntity(Role entity) {
        log.debug("Mapping Role to RoleDTO");
        return modelMapper.map(entity, RoleDTO.class);
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        log.debug("Mapping RoleDTO to Role");
        return modelMapper.map(dto, Role.class);
    }
}
