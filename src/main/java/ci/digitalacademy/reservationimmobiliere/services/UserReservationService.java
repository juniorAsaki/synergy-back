package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.User_reservationDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserReservationService {


    User_reservationDTO save(User_reservationDTO userReservationDTO);

    Optional<User_reservationDTO> getById(Long id);

    Optional<User_reservationDTO> getBySlug(String slug);


    User_reservationDTO saveReservation(User_reservationDTO userReservationDTO);

    List<User_reservationDTO> getAll();

    BigDecimal calculateTotalAmount(Long residenceId, LocalDate startDate, LocalDate endDate);

    boolean isPropertyAvailable(Long residenceId, LocalDate startDate, LocalDate endDate);
}
