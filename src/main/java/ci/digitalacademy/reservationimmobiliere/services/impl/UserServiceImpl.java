package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.UserRepository;
import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save user {}", userDTO);
        String password = userDTO.getPassword();
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.fromEntity(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update user {}", userDTO);
        return getById(userDTO.getId()).map(user -> {
            user.setEmail(userDTO.getEmail());
            return save(user);
        }).orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id).map(user -> {
            return userMapper.fromEntity(user);
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getByEmail(String email) {
        log.debug("Request to get User by email : {}", email);
        return userRepository.findByEmail(email).map(user -> {
            return userMapper.fromEntity(user);
        }).orElse(null);
    }

}
