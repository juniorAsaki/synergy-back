package ci.digitalacademy.reservationimmobiliere.services.impl;



import ci.digitalacademy.reservationimmobiliere.Repository.ImageRepository;
import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.services.ImageService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ImageMapper; // Assurez-vous d'avoir un mapper pour les DTOs
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final ResidenceService residenceService;

    @Override
    public ImageDTO addImage(ImageDTO imageDTO) {
        Optional<ResidenceDTO> byId = residenceService.getById(imageDTO.getResidence().getId());
        if(byId.isPresent()) {
            imageDTO.setResidence(byId.get());
        }
        Image image = imageMapper.toEntity(imageDTO);
        image.setSlug(SlugifyUtils.generate(imageDTO.getDescription()));

        image.setDateUpload(LocalDate.now()); // Assigne la date d'upload
        Image savedImage = imageRepository.save(image);
        return imageMapper.fromEntity(savedImage);
    }

    @Override
    public List<ImageDTO> getImagesByResidence(Long residenceId) {
        List<Image> images = imageRepository.findByResidenceId(residenceId); // Une méthode pour obtenir les images par ID de résidence
        return images.stream()
                .map(imageMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDTO updateImage(Long id, ImageDTO imageDTO) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        image.setDescription(imageDTO.getDescription());
        image.setSlug(imageDTO.getSlug());
        // Ne pas changer l'URL ici, car elle est souvent fixe après upload
        Image updatedImage = imageRepository.save(image);
        return imageMapper.fromEntity(updatedImage);
    }

    @Override
    public ImageDTO getImageBySlug(String slug) {
        Optional<Image> imageOptional = imageRepository.findBySlug(slug); // Supposons que vous avez cette méthode dans votre repository
        if (imageOptional.isPresent()) {
            return imageMapper.fromEntity(imageOptional.get()); // Mapper l'entité vers le DTO
        } else {
            throw new RuntimeException("Image not found for slug: " + slug); // Gérer l'absence d'image
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id); // Méthode pour supprimer l'image
    }
}
