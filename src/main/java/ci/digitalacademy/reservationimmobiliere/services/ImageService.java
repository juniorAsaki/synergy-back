package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;

import java.util.List;

public interface ImageService {

     ImageDTO addImage(ImageDTO imageDTO);
     List<ImageDTO> getImagesByResidence(Long residenceId);
     ImageDTO updateImage(Long id, ImageDTO imageDTO);
     void deleteImage(Long id);
     ImageDTO getImageBySlug(String slug);
}
