package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.services.ReviewService;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/other/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewResource {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "save review", description = "this endpoint allow to save review")
    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {
        log.debug("Request to create a new review : {}", reviewDTO);
        return new ResponseEntity<>(reviewService.saveReview(reviewDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "get all review", description = "this endpoint allow to get all review")
    public List<ReviewDTO> getAllReviews() {
        log.debug("Request to get all reviews");
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get review by id", description = "this endpoint allow to get review by id")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        log.debug("Request to get Review by id: {}", id);
        return new ResponseEntity<>(reviewService.getReviewById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request to get review"),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        log.debug("Request to update Review : {}", id);
        return new ResponseEntity<>(reviewService.updateReview(id, reviewDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete review", description = "this endpoint allow to delete review")
    public void deleteReview(@PathVariable Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewService.deleteReview(id);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "get review by slug", description = "this endpoint allow to get review by slug")
    public ResponseEntity<?> getReviewBySlug(@PathVariable String slug) {
        log.debug("Request to get Review by slug: {}", slug);
        return new ResponseEntity<>(reviewService.getReviewBySlug(slug), HttpStatus.OK);
    }
}

