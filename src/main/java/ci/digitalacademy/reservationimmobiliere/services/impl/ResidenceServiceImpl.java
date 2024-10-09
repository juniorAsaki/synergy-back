package ci.digitalacademy.reservationimmobiliere.services.impl;



import ci.digitalacademy.reservationimmobiliere.Repository.AddressRepository;
import ci.digitalacademy.reservationimmobiliere.Repository.ResidenceRepository;
import ci.digitalacademy.reservationimmobiliere.models.Address;
import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.services.AddressService;
import ci.digitalacademy.reservationimmobiliere.services.FileStorageService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.ReviewService;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.AddressDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ResidenceMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidenceServiceImpl implements ResidenceService {

    private final ResidenceRepository residenceRepository;
    private final FileStorageService fileStorageService;
    private final ResidenceMapper residenceMapper; // Mapper pour les résidences
    private final AddressService addressService;
    private final AddressRepository addressRepository;


    @Override
    public Optional<ResidenceDTO> getById(Long id) {

        return residenceRepository.findById(id).map(residenceMapper::fromEntity);
    }

    public ResidenceDTO createResidence(ResidenceDTO residenceDTO) throws IOException {
        // Gestion de l'adresse
        if (residenceDTO.getAddress() != null && residenceDTO.getAddress().getId() != null) {
            Optional<AddressDTO> addressOpt = addressService.findById(residenceDTO.getAddress().getId());

            if (addressOpt.isPresent()) {
                residenceDTO.setAddress(addressOpt.get());  // Réutiliser l'adresse existante
            } else {
                throw new RuntimeException("Address not found");
            }
        } else if (residenceDTO.getAddress() != null) {
            // Si l'adresse n'a pas d'ID (donc nouvelle), il faut la sauvegarder
            Address address = new Address();
            address.setCity(residenceDTO.getAddress().getCity());
            // Ajouter d'autres champs si nécessaire...
            Address savedAddress = addressRepository.save(address);  // Sauvegarder l'adresse
            residenceDTO.getAddress().setId(savedAddress.getId());  // Mettre à jour le DTO
        } else {
            throw new RuntimeException("Address is null or does not have an ID");
        }

        // Mapper le DTO Residence vers l'entité Residence
        Residence residence = residenceMapper.toEntity(residenceDTO);

        // Gestion de l'upload des images
        List<Image> uploadedImages = new ArrayList<>();
        if (residenceDTO.getImageFiles() != null && !residenceDTO.getImageFiles().isEmpty()) {
            for (MultipartFile file : residenceDTO.getImageFiles()) {
                String imageUrl = fileStorageService.upload(file);  // Uploader l'image
                Image image = new Image();
                image.setImageUrl(imageUrl);  // Stocker l'URL de l'image
                uploadedImages.add(image);  // Ajouter l'image à la liste
            }
        }
        residence.setImages(uploadedImages);  // Associer les images à la résidence

        // Générer le slug
        residence.setSlug(SlugifyUtils.generate(residenceDTO.getDescription()));

        // Sauvegarder la résidence dans la base de données
        Residence savedResidence = residenceRepository.save(residence);

        // Retourner le DTO de la résidence sauvegardée
        return residenceMapper.fromEntity(savedResidence);
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

    public ResidenceDTO getResidenceBySlug(String slug) {
        Residence residence = residenceRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Residence not found for slug: " + slug));
        return residenceMapper.fromEntity(residence);
    }



    public void deleteResidence(Long id) {
        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Residence not found"));
        residenceRepository.delete(residence);
    }
}



