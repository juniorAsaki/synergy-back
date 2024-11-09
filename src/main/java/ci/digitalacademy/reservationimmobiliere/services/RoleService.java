package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    RoleDTO save(RoleDTO roleDTO);

    RoleDTO getByRole(String roleUser);

    Optional<RoleDTO> getById(Long id);


}
