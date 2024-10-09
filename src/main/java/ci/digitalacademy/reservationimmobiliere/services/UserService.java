package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    Optional<UserDTO> getById(Long id);

    void delete(Long id);
}
