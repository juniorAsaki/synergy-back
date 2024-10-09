package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.ReservationRepository;
import ci.digitalacademy.reservationimmobiliere.models.Reservation;
import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.ReservationService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ReservationDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.ReservationMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CustomerService customerService;
    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        log.debug("Request to save Reservation : {}", reservationDTO);
        Optional<CustomerDTO> customerDTO = customerService.getById(reservationDTO.getCustomer().getId());
        if (customerDTO.isPresent()) {
            reservationDTO.setCustomer(customerDTO.get());
        }
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation = reservationRepository.save(reservation);
        return reservationMapper.fromEntity(reservation);
    }

    @Override
    public Optional<ReservationDTO> getById(Long id) {
        log.debug("Request to get Reservation by id : {}", id);
        return reservationRepository.findById(id).map(reservation -> {
            return reservationMapper.fromEntity(reservation);
        });
    }

    @Override
    public Optional<ReservationDTO> getBySlug(String slug) {
        log.debug("Request to get Reservation by slug : {}", slug);
        return reservationRepository.findBySlug(slug).map(reservation -> {
            return reservationMapper.fromEntity(reservation);
        });
    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        log.debug("Request to save Reservation : {}", reservationDTO);
        final String slug = SlugifyUtils.generate(reservationDTO.getCustomer().getLastName());
        return null;
    }

    @Override
    public List<ReservationDTO> getAll() {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAll().stream().map(reservation -> {
            return reservationMapper.fromEntity(reservation);
        }).toList();
    }
}
