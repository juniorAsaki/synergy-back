package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.ResidenceRepository;
import ci.digitalacademy.reservationimmobiliere.Repository.UserReservationRepository;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.models.UserReservation;
import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.UserReservationService;
import ci.digitalacademy.reservationimmobiliere.services.dto.CustomerDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.ResidenceDTO;
import ci.digitalacademy.reservationimmobiliere.services.dto.User_reservationDTO;
import ci.digitalacademy.reservationimmobiliere.services.mapper.UserReservationMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserReservationServiceImpl implements UserReservationService {

    private final UserReservationRepository userReservationRepository;
    private final ResidenceRepository residenceRepository;
    private final UserReservationMapper userReservationMapper;
    private final CustomerService customerService;
    private final ResidenceService residenceService;
    @Override
    public User_reservationDTO save(User_reservationDTO userReservationDTO) {
        log.debug("Request to save Reservation : {}", userReservationDTO);
        Optional<CustomerDTO> customerDTO = customerService.getById(userReservationDTO.getCustomer().getIdPerson());
        Optional<ResidenceDTO> residenceDTO = residenceService.getResidenceById(userReservationDTO.getResidence().getId());
        if (customerDTO.isPresent() && residenceDTO.isPresent()) {
            userReservationDTO.setCustomer(customerDTO.get());
            userReservationDTO.setResidence(residenceDTO.get());
            BigDecimal totalAmount = calculateTotalAmount(
                    userReservationDTO.getResidence().getId(),
                    userReservationDTO.getStartDate(),
                    userReservationDTO.getEndDate()
            );
            userReservationDTO.setTotalAmount(totalAmount);
        }
        UserReservation userReservation = userReservationMapper.toEntity(userReservationDTO);
        userReservation = userReservationRepository.save(userReservation);
        return userReservationMapper.fromEntity(userReservation);
    }


    @Override
    public Optional<User_reservationDTO> getById(Long id) {
        log.debug("Request to get Reservation by id : {}", id);
        return userReservationRepository.findById(id).map(userReservation -> {
            return userReservationMapper.fromEntity(userReservation);
        });
    }

    @Override
    public Optional<User_reservationDTO> getBySlug(String slug) {
        log.debug("Request to get Reservation by slug : {}", slug);
        return userReservationRepository.findBySlug(slug).map(userReservation -> {
            return userReservationMapper.fromEntity(userReservation);
        });
    }

    @Override
    public User_reservationDTO saveReservation(User_reservationDTO userReservationDTO) {
        log.debug("Request to save Reservation : {}", userReservationDTO);
        final String slug = SlugifyUtils.generate(userReservationDTO.getCustomer().getLastName());
        userReservationDTO.setSlug(slug);
        return save(userReservationDTO);
    }

    @Override
    public List<User_reservationDTO> getAll() {
        log.debug("Request to get all Reservations");
        return userReservationRepository.findAll().stream().map(userReservation -> {
            return userReservationMapper.fromEntity(userReservation);
        }).toList();
    }

    public BigDecimal calculateTotalAmount(Long residenceId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début doit être antérieure ou égale à la date de fin");
        }
        Residence residence = residenceRepository.findById(residenceId)
                .orElseThrow(() -> new IllegalArgumentException("Résidence non trouvée"));
        BigDecimal price = residence.getPrice();
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le prix doit être défini et positif");
        }
        long duration = ChronoUnit.DAYS.between(startDate, endDate);
        if (duration == 0) {
            duration = 1;
        }
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(duration));

        return totalAmount;
    }

    public boolean isPropertyAvailable(Long residenceId, LocalDate startDate, LocalDate endDate) {
        List<User_reservationDTO> existingReservations = userReservationRepository.findByResidenceId(residenceId);

        for (User_reservationDTO reservation : existingReservations) {
            if (dateRangesOverlap(startDate, endDate, reservation.getStartDate(), reservation.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    private boolean dateRangesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
}
