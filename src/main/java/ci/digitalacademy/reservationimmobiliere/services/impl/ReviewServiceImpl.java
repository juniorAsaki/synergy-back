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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CustomerService customerService;
    private final ResidenceService residenceService;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        log.debug("Request to create a new review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setSlug(SlugifyUtils.generate(reviewDTO.getComment()));
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.fromEntity(savedReview);
    }


    @Override
    public List<ReviewDTO> getAllReviews() {
        log.debug("Request to get all reviews");
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(reviewMapper::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<ReviewDTO> getReviewById(Long id) {
        log.debug("Request to get Review by id {}", id);
        return reviewRepository.findById(id)
                .map(reviewMapper::fromEntity);
    }


    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        log.debug("Request to update Review : {}", reviewDTO);
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setComment(reviewDTO.getComment());
        existingReview.setDate(reviewDTO.getDate());
        existingReview.setSlug(reviewDTO.getSlug());

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.fromEntity(updatedReview);
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Optional<CustomerDTO> customerDTO = customerService.getById(reviewDTO.getCustomer().getIdPerson());
        Optional<ResidenceDTO> residenceDTO = residenceService.getResidenceById(reviewDTO.getResidence().getId());
        if (customerDTO.isPresent() && residenceDTO.isPresent()) {

            reviewDTO.setCustomer(customerDTO.get());
            reviewDTO.setResidence(residenceDTO.get());
        }
        final String slug = SlugifyUtils.generate(reviewDTO.getCustomer().getFirstName());
        reviewDTO.setSlug(slug);
        return saveReview(reviewDTO);

    }

    @Override
    public void deleteReview(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.deleteById(id);
    }

    @Override
    public Optional<ReviewDTO> getReviewBySlug(String slug) {
        log.debug("Request to get Review by slug {}", slug);
        return reviewRepository.findBySlug(slug).map(reviewMapper::fromEntity);
    }
}

