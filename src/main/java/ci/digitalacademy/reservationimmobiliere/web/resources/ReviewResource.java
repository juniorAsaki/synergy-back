package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.services.ReviewService;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewResource {

    private final ReviewService reviewService;

    // Créer un nouvel avis
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        log.debug("Request to create a new review : {}", reviewDTO);

        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(createdReview);
    }

    // Récupérer tous les avis
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        log.debug("Request to get all reviews");

        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // Récupérer un avis par ID
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        log.debug("Request to get Review : {}", id);

        ReviewDTO review = reviewService.getReviewById(id).orElseThrow(()->new IllegalArgumentException("review not found"));
        return ResponseEntity.ok(review);
    }

    // Mettre à jour un avis
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        log.debug("Request to update Review : {}", id);

        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    // Supprimer un avis
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.debug("Request to delete Review : {}", id);

        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}

