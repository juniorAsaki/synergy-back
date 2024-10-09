package ci.digitalacademy.reservationimmobiliere.web.resources;

import ci.digitalacademy.reservationimmobiliere.services.RoleService;
import ci.digitalacademy.reservationimmobiliere.services.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleResource {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO roleUser) {
        log.debug("Request to save Role : {}", roleUser);
        return new ResponseEntity<>(roleService.save(roleUser), HttpStatus.CREATED);
    }
}
