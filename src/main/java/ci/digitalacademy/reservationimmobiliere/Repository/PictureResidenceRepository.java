package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.PictureResidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PictureResidenceRepository extends JpaRepository<PictureResidence, Long> {
    List<PictureResidence> findByResidenceId(Long residenceId);
    Optional<PictureResidence> findBySlug(String slug);

}
