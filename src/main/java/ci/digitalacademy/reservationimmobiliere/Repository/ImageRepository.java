package ci.digitalacademy.reservationimmobiliere.Repository;

import ci.digitalacademy.reservationimmobiliere.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByResidenceId(Long residenceId);
    Optional<Image> findBySlug(String slug); // Méthode pour trouver une image par son slug

}
