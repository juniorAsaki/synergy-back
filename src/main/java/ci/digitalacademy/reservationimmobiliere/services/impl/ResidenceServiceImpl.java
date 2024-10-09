package ci.digitalacademy.reservationimmobiliere.services.impl;



import ci.digitalacademy.reservationimmobiliere.Repository.ResidenceRepository;
import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.models.Review;
import ci.digitalacademy.reservationimmobiliere.services.FileStorageService;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ImageMapper;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ResidenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidenceServiceImpl {

    private final ResidenceRepository residenceRepository;
    private final FileStorageService fileStorageService;
    private final ResidenceMapper residenceMapper; // Mapper pour les résidences

    public ResidenceDTO createResidence(ResidenceDTO residenceDTO) throws IOException {
        Residence residence = residenceMapper.toEntity(residenceDTO); // Mapper directement le DTO vers l'entité

        // Gérer l'upload des images
        List<Image> uploadedImages = new ArrayList<>();
        if (residenceDTO.getImageFiles() != null) {
            for (MultipartFile file : residenceDTO.getImageFiles()) {
                String imageUrl = fileStorageService.upload(file); // Upload l'image
                Image image = new Image();
                image.setImageUrl(imageUrl); // Stockons l'URL
                uploadedImages.add(image); // Ajoutons à la liste
            }
        }
        residence.setImages(uploadedImages); // Ajoutons les images à la résidence

        // Mapper l'adresse
        if (residenceDTO.getAddress() != null) {
            Address address = new Address();
            address.setCity(residenceDTO.getAddress().getCity());
            // Ajoutez d'autres champs nécessaires
            residence.setAddress(address);
        }

        // Sauvegarder la résidence dans la base de données
        Residence savedResidence = residenceRepository.save(residence);

        // Mapper à ResidenceDTO
        return residenceMapper.fromEntity(savedResidence); // Renvoyer la résidence sauvegardée
    }

    public List<ResidenceDTO> getAllResidences() {
        List<Residence> residences = residenceRepository.findAll();
        return residences.stream()
                .map(residenceMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public ResidenceDTO getResidenceById(Long id) {
        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Residence not found"));
        return residenceMapper.fromEntity(residence);
    }

    public ResidenceDTO updateResidence(Long id, ResidenceDTO residenceDTO) throws IOException {
        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Residence not found"));

        // Mapper directement le DTO vers l'entité pour la mise à jour
        residence = residenceMapper.toEntity(residenceDTO); // Remplace l'ancienne résidence par la nouvelle

        // Mettre à jour l'adresse (si nécessaire)
        if (residenceDTO.getAddress() != null) {
            Address address = new Address();
            address.setCity(residenceDTO.getAddress().getCity());
            residence.setAddress(address);
        }

        // Mettre à jour les images si de nouveaux fichiers sont fournis
        if (residenceDTO.getImageFiles() != null) {
            List<Image> uploadedImages = new ArrayList<>();
            for (MultipartFile file : residenceDTO.getImageFiles()) {
                String imageUrl = fileStorageService.upload(file); // Upload l'image
                Image image = new Image();
                image.setImageUrl(imageUrl); // Stockons l'URL
                uploadedImages.add(image); // Ajoutons à la liste
            }
            residence.setImages(uploadedImages); // Remplacer les images existantes
        }

        // Sauvegarder la résidence mise à jour dans la base de données
        Residence updatedResidence = residenceRepository.save(residence);
        return residenceMapper.fromEntity(updatedResidence); // Renvoyer la résidence mise à jour
    }

    public void deleteResidence(Long id) {
        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Residence not found"));
        residenceRepository.delete(residence);
    }
}



