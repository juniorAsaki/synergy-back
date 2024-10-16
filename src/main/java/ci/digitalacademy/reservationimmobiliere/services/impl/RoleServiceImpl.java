package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.RoleRepository;
import ci.digitalacademy.reservationimmobiliere.models.Role;
import ci.digitalacademy.reservationimmobiliere.services.RoleService;
import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleUserMapper;

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        Role roleUser = roleUserMapper.toEntity(roleDTO);
        roleUser = roleRepository.save(roleUser);
        return roleUserMapper.fromEntity(roleUser);
    }

    @Override
    public RoleDTO getByRole(String roleUser) {
        log.debug("Request to get RoleUser : {}", roleUser);
        return roleUserMapper.fromEntity(roleRepository.getByRole(roleUser));
    }

    @Override
    public Optional<RoleDTO> getById(Long id) {
        log.debug("Request to get RoleUser by id {}", id);
        return roleRepository.findById(id).map(role -> {
            return roleUserMapper.fromEntity(role);
        });
    }

}
