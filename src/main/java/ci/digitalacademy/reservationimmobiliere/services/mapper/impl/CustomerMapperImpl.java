package ci.digitalacademy.reservationimmobiliere.services.mapper.impl;

import ci.digitalacademy.reservationimmobiliere.models.Customer;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapperImpl implements CustomerMapper {
    private final ModelMapper modelMapper;
    @Override
    public CustomerDTO fromEntity(Customer entity) {
        return modelMapper.map(entity, CustomerDTO.class);
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }
}
