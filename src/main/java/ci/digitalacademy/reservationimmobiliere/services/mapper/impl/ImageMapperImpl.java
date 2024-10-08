package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Image;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.services.dto.ImageDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageMapperImpl implements ImageMapper {

    private final ModelMapper modelMapper;
    @Override
    public ImageDTO fromEntity(Image entity) {
        return modelMapper.map(entity, ImageDTO.class);
    }

    @Override
    public Image toEntity(ImageDTO dto) {
        return modelMapper.map(dto, Image.class);
    }
}
