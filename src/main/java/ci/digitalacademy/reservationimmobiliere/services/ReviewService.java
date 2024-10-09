package ci.digitalacademy.reservationimmobiliere.services;



import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getAllReviews();

    Optional<ReviewDTO> getReviewById(Long id);

    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);

    void deleteReview(Long id);
}

