package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {

    Optional<Residence> findBySlug(String slug);
    List<Residence> findAllByOwner_IdPerson(Long idPerson);

}
