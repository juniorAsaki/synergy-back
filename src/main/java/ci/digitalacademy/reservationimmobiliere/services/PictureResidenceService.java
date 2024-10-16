package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.PictureResidenceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PictureResidenceService {

     PictureResidenceDTO save(PictureResidenceDTO pictureResidenceDTO);
     List<PictureResidenceDTO> getImagesByResidence(Long residenceId);
     Optional<PictureResidenceDTO> getImageBySlug(String slug);
     Optional <PictureResidenceDTO> getImageById(Long id);
     List<PictureResidenceDTO> getAllImages();
     void saveAllPictureResidence(List<MultipartFile> pictures, Long id);
     void deleteImage(Long id);
}
