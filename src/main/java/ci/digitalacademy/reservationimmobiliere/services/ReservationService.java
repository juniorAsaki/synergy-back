package ci.digitalacademy.reservationimmobiliere.services;

import ci.digitalacademy.reservationimmobiliere.services.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    ReservationDTO save(ReservationDTO reservationDTO);

    Optional<ReservationDTO> getById(Long id);

    Optional<ReservationDTO> getBySlug(String slug);


    ReservationDTO saveReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> getAll();
}
