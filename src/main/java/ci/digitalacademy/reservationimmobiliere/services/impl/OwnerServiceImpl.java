package ci.digitalacademy.reservationimmobiliere.services.impl;


import ci.digitalacademy.reservationimmobiliere.Repository.OwnerRepository;
import ci.digitalacademy.reservationimmobiliere.models.Owner;
import ci.digitalacademy.reservationimmobiliere.models.Role;
import ci.digitalacademy.reservationimmobiliere.security.AuthorityConstants;
import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.OwnerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.UserDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.OwnerMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserService userService;


    @Override
    public OwnerDTO save(OwnerDTO ownerDTO) {
        log.debug("Request to save Owner {}", ownerDTO);
        Owner  owner = ownerMapper.toEntity(ownerDTO);
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
        return getById(ownerDTO.getIdPerson()).map(owner ->{
            owner.setFirstName(ownerDTO.getFirstName());
            owner.setLastName(ownerDTO.getLastName());
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
    public OwnerDTO saveOwner(OwnerDTO ownerDTO) {
        log.debug("Request to save Owner {}", ownerDTO);
        RoleDTO role = new RoleDTO();
        role.setRole(AuthorityConstants.ROLE_OWNER);
        if (ownerDTO.getUser() != null) {
            ownerDTO.getUser().setRole(role);
            UserDTO savedUser = userService.registrationAndSendOTP(ownerDTO.getUser());
            ownerDTO.setUser(savedUser);

        }
        final String slug = SlugifyUtils.generate(ownerDTO.getLastName());
        ownerDTO.setSlug(slug);
        return save(ownerDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner by id {}", id);
        ownerRepository.deleteById(id);

    }

    @Override
    public Optional<OwnerDTO> findByUserId(Long id) {
        log.debug("Request to get Owner by user id {}", id);
        return ownerRepository.findByUserId(id).map(owner -> {
            return ownerMapper.fromEntity(owner);
        });
    }


}

