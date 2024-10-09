package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.ReviewRepository;
import ci.digitalacademy.reservationimmobiliere.models.Review;
import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.ReviewService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ReviewMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CustomerService customerService;
    private final ResidenceService residenceService;

    // Créer un nouvel avis
    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Optional<CustomerDTO> byId = customerService.getById(reviewDTO.getCustomer().getId());
        Optional<ResidenceDTO> byIdd = residenceService.getById(reviewDTO.getCustomer().getId());

        if (byId.isPresent() && byIdd.isPresent()) {

        reviewDTO.setCustomer(byId.get());
        reviewDTO.setResidence(byIdd.get());
        }

        Review review = reviewMapper.toEntity(reviewDTO);  // Mapper le DTO vers l'entité
        review.setSlug(SlugifyUtils.generate(reviewDTO.getComment()));
        Review savedReview = reviewRepository.save(review);  // Sauvegarder l'avis dans la base
        return reviewMapper.fromEntity(savedReview);  // Retourner l'avis sauvegardé sous forme de DTO
    }



    // Récupérer tous les avis
    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();  // Récupérer tous les avis
        return reviews.stream()
                .map(reviewMapper::fromEntity)  // Mapper chaque avis en DTO
                .collect(Collectors.toList());
    }

    // Récupérer un avis par ID
    @Override
    public Optional<ReviewDTO> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::fromEntity);  // Mapper l'entité si elle existe
    }


    // Mettre à jour un avis
    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setComment(reviewDTO.getComment());
        existingReview.setDate(reviewDTO.getDate());
        existingReview.setSlug(reviewDTO.getSlug());
        // Si d'autres champs doivent être mis à jour, ajouter ici

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.fromEntity(updatedReview);  // Retourner l'avis mis à jour
    }

    // Supprimer un avis
    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);  // Supprimer l'avis
    }
}

