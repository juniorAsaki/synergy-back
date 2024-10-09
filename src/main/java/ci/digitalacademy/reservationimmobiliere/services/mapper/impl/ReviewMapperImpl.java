package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.models.Review;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReviewDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ReviewMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDTO fromEntity(Review entity) {
        return modelMapper.map(entity, ReviewDTO.class);
    }

    @Override
    public Review toEntity(ReviewDTO dto) {
        return modelMapper.map(dto, Review.class);
    }
}
