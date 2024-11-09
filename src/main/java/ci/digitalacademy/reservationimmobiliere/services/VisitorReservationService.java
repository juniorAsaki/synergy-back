package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.RequestReservationOtherCustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorReservationDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitorReservationService {

    VisitorReservationDTO save(VisitorReservationDTO visitorReservationDTO);

    Optional<VisitorReservationDTO> getById(Long id);

    Optional<VisitorReservationDTO> getBySlug(String slug);


    VisitorReservationDTO saveReservation(VisitorReservationDTO visitorReservationDTO);

    List<VisitorReservationDTO> getAll();

    BigDecimal calculateTotalAmount(Long residenceId, LocalDate startDate, LocalDate endDate);

    boolean isPropertyAvailable(Long residenceId, LocalDate startDate, LocalDate endDate);

    VisitorReservationDTO saveReservationForOtherCustomer(RequestReservationOtherCustomerDTO requestReservationOtherCustomerDTO);
}
