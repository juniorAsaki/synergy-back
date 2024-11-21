package ci.digitalacademy.reservationimmobiliere.services.impl;



import ci.digitalacademy.reservationimmobiliere.Repository.PictureResidenceRepository;
import ci.digitalacademy.reservationimmobiliere.models.PictureResidence;
import ci.digitalacademy.reservationimmobiliere.services.FileStorageService;
import ci.digitalacademy.reservationimmobiliere.services.PictureResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.dto.PictureResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ImageMapper; // Assurez-vous d'avoir un mapper pour les DTOs
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PictureResidenceServiceImpl implements PictureResidenceService {

    private final PictureResidenceRepository imageRepository;
    private final ImageMapper imageMapper;
    private final ResidenceService residenceService;
    private final FileStorageService fileStorageService;

    @Override
    public PictureResidenceDTO save(PictureResidenceDTO pictureResidenceDTO) {
        log.debug("Request to save PictureResidence {}", pictureResidenceDTO);
        PictureResidence pictureResidence = imageMapper.toEntity(pictureResidenceDTO);
        pictureResidence = imageRepository.save(pictureResidence);
        return imageMapper.fromEntity(pictureResidence);
    }

    @Override
    public List<PictureResidenceDTO> getImagesByResidence(Long residenceId) {
        log.debug("Request to find image by residence {}", residenceId);
        List<PictureResidence> PictureResidences = imageRepository.findByResidenceId(residenceId);
        return PictureResidences.stream()
                .map(imageMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PictureResidenceDTO> getImageBySlug(String slug) {
        log.debug("Request to find image by slug {}", slug);
        return imageRepository.findBySlug(slug).map(image -> {
            return imageMapper.fromEntity(image);
        });

    }

    @Override
    public Optional<PictureResidenceDTO> getImageById(Long id) {
        log.debug("Request to find image by id {}", id);
        return imageRepository.findById(id).map(image -> {
            return imageMapper.fromEntity(image);
        });
    }

    @Override
    public List<PictureResidenceDTO> getAllImages() {
        log.debug("Request to get all images");
        return imageRepository.findAll().stream().map(image ->{
            return imageMapper.fromEntity(image);
        }).toList();
    }

    @Override
    public void saveAllPictureResidence(List<MultipartFile> pictures, Long id) {
        log.debug("Adding image {}", pictures);
        ResidenceDTO residenceDTO = residenceService.getResidenceById(id).orElseThrow(()->new IllegalStateException("Residence not found"));
        pictures.forEach(picture -> {
            try {
                String url = fileStorageService.upload(picture);
                PictureResidenceDTO imageDTO = new PictureResidenceDTO();
                imageDTO.setResidence(residenceDTO);
                imageDTO.setImageUrl(url);
                String slug = SlugifyUtils.generate(residenceDTO.getName());
                imageDTO.setSlug(slug);
                save(imageDTO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void deleteImage(Long id) {
        log.debug("Request to delete image by id {}", id);
        imageRepository.deleteById(id);
    }
}
