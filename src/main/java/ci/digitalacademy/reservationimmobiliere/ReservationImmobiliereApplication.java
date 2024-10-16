package ci.digitalacademy.reservationimmobiliere;

import ci.digitalacademy.reservationimmobiliere.Repository.RoleRepository;
import ci.digitalacademy.reservationimmobiliere.models.Role;
import ci.digitalacademy.reservationimmobiliere.security.AuthorityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ReservationImmobiliereApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;


    public static void main(String[] args) {
        SpringApplication.run(ReservationImmobiliereApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> all = roleRepository.findAll();
        if (all.isEmpty()) {
            Role role = new Role();
            Role role1 = new Role();
            role.setRole(AuthorityConstants.ROLE_OWNER);
            role1.setRole(AuthorityConstants.ROLE_CUSTOMER);
            roleRepository.save(role);
            roleRepository.save(role1);

        }
    }
}
