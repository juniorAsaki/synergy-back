package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Review;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewMapperImpl implements ReviewMapper {

    private final ModelMapper modelMapper;

    @Override
    public ReviewDTO fromEntity(Review entity) {
        log.debug("Mapping Review to ReviewDTO");
        return modelMapper.map(entity, ReviewDTO.class);
    }

    @Override
    public Review toEntity(ReviewDTO dto) {
        log.debug("Mapping ReviewDTO to Review");
        return modelMapper.map(dto, Review.class);
    }
}
