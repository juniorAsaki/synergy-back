package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.OtherCustomer;
import ci.digitalacademy.reservationimmobiliere.services.dto.OtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.OtherCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtherCustomerMapperImpl implements OtherCustomerMapper {

    private final ModelMapper modelMapper;
    @Override
    public OtherCustomerDTO fromEntity(OtherCustomer entity) {
        log.debug("Mapping OtherCustomerService to OtherCustomerDTO");
        return modelMapper.map(entity, OtherCustomerDTO.class);
    }

    @Override
    public OtherCustomer toEntity(OtherCustomerDTO dto) {
        log.debug("Mapping OtherCustomerDTO to OtherCustomerService");
        return modelMapper.map(dto, OtherCustomer.class);
    }

}
