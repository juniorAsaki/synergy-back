package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Customer;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerMapperImpl implements CustomerMapper {
    private final ModelMapper modelMapper;
    @Override
    public CustomerDTO fromEntity(Customer entity) {
        log.debug("Mapping Customer to CustomerDTO");
        return modelMapper.map(entity, CustomerDTO.class);
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        log.debug("Mapping CustomerDTO to Customer");
        return modelMapper.map(dto, Customer.class);
    }
}
