package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.OwnerRepository;
import ci.digitalacademy.reservationimmobiliere.Repository.UserRepository;
import ci.digitalacademy.reservationimmobiliere.models.Owner;
import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.security.AuthorityConstants;
import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.RoleService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.RegistrationUserAndOwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.OwnerMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Override
    public RegistrationUserAndOwnerDTO saveOwnerAndUser(RegistrationUserAndOwnerDTO registrationUserAndOwnerDTO) {
        log.debug("Request to save Owner {}", registrationUserAndOwnerDTO);
        RoleDTO byRole = roleService.getByRole(AuthorityConstants.ROLE_PROPERTY);
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(byRole);
        userDTO.setEmail(registrationUserAndOwnerDTO.getEmail());
        userDTO.setPassword(bCryptPasswordEncoder.encode(registrationUserAndOwnerDTO.getPassword()));
        UserDTO save = userService.save(userDTO);
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setFirstName(registrationUserAndOwnerDTO.getFirstName());
        ownerDTO.setLastName(registrationUserAndOwnerDTO.getLastName());
        ownerDTO.setPhoneNumber(registrationUserAndOwnerDTO.getPhoneNumber());
        ownerDTO.setGender(String.valueOf(registrationUserAndOwnerDTO.getGender()));
        ownerDTO.setUser(save);
        final String slug = SlugifyUtils.generate(ownerDTO.getFirstName());
        ownerDTO.setSlug(slug);
        Owner ownerr = ownerMapper.toEntity(ownerDTO);
        ownerr = ownerRepository.save(ownerr);
        ownerMapper.fromEntity(ownerr);
        return registrationUserAndOwnerDTO;
    }

    @Override
    public OwnerDTO save(OwnerDTO ownerDTO) {
        log.debug("Request to save Owner {}", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.fromEntity(owner);
    }

    @Override
    public Optional<OwnerDTO> getById(Long id) {
        log.debug("Request to get Owner by id {}", id);
        return ownerRepository.findById(id).map(owner -> {
            return ownerMapper.fromEntity(owner);
        });
    }

    @Override
    public List<OwnerDTO> getAll() {
        log.debug("Request to get all Owner");
        return ownerRepository.findAll().stream().map(owner -> {
            return ownerMapper.fromEntity(owner);
        }).toList();
    }

    @Override
    public Optional<OwnerDTO> getBySlug(String slug) {
        log.debug("Request to get Owner by slug {}", slug);
        return ownerRepository.findBySlug(slug).map(owner -> {
            return ownerMapper.fromEntity(owner);
        });
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) {
        log.debug("Request to update Owner {}", ownerDTO);
        return getById(ownerDTO.getId()).map(owner ->{
            owner.setFirstName(ownerDTO.getFirstName());
            owner.setLastName(ownerDTO.getLastName());
            owner.setEmail(ownerDTO.getEmail());
            owner.setPhoneNumber(ownerDTO.getPhoneNumber());
            return save(ownerDTO);
        }).orElseThrow(()-> new IllegalStateException());
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO, Long id) {
        log.debug("Request to update Owner {}", ownerDTO);
        return ownerDTO;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner by id {}", id);
        ownerRepository.deleteById(id);

    }


}
