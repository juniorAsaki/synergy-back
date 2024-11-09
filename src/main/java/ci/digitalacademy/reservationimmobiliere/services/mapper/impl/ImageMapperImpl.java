package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.PictureResidence;
import ci.digitalacademy.reservationimmobiliere.services.dto.PictureResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageMapperImpl implements ImageMapper {

    private final ModelMapper modelMapper;
    @Override
    public PictureResidenceDTO fromEntity(PictureResidence entity) {
        log.debug("Mapping PictureResidence to PictureResidenceDTO");
        return modelMapper.map(entity, PictureResidenceDTO.class);
    }

    @Override
    public PictureResidence toEntity(PictureResidenceDTO dto) {
        log.debug("Mapping PictureResidenceDTO to PictureResidence");
        return modelMapper.map(dto, PictureResidence.class);
    }
}
