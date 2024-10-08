package ci.digitalacademy.reservationimmobiliere.services.impl;



import ci.digitalacademy.reservationimmobiliere.Repository.ImageRepository;
import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ImageMapper; // Assurez-vous d'avoir un mapper pour les DTOs
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceImpl {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageDTO addImage(ImageDTO imageDTO) {
        Image image = imageMapper.toEntity(imageDTO);
        image.setDateUpload(LocalDate.now()); // Assigne la date d'upload
        Image savedImage = imageRepository.save(image);
        return imageMapper.fromEntity(savedImage);
    }

    public List<ImageDTO> getImagesByResidence(Long residenceId) {
        List<Image> images = imageRepository.findByResidenceId(residenceId); // Une méthode pour obtenir les images par ID de résidence
        return images.stream()
                .map(imageMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public ImageDTO updateImage(Long id, ImageDTO imageDTO) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        image.setDescription(imageDTO.getDescription());
        image.setSlug(imageDTO.getSlug());
        // Ne pas changer l'URL ici, car elle est souvent fixe après upload
        Image updatedImage = imageRepository.save(image);
        return imageMapper.fromEntity(updatedImage);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id); // Méthode pour supprimer l'image
    }
}
