package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.VisitorVeservationDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitorReservationService {

    VisitorVeservationDTO save(VisitorVeservationDTO visitorReservationDTO);

    Optional<VisitorVeservationDTO> getById(Long id);

    Optional<VisitorVeservationDTO> getBySlug(String slug);


    VisitorVeservationDTO saveReservation(VisitorVeservationDTO visitorReservationDTO);

    List<VisitorVeservationDTO> getAll();

    BigDecimal calculateTotalAmount(Long residenceId, LocalDate startDate, LocalDate endDate);

    boolean isPropertyAvailable(Long residenceId, LocalDate startDate, LocalDate endDate);
}
