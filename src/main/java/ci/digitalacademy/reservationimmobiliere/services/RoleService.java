package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;

import java.util.Optional;

public interface RoleService {

    RoleDTO save (RoleDTO roleDTO);

    Optional<RoleDTO> getById (Long id);





}
