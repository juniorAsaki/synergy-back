package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.VisitorReservationRepository;
import ci.digitalacademy.reservationimmobiliere.models.VisitorReservation;
import ci.digitalacademy.reservationimmobiliere.services.OtherCustomerService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.VisitorReservationService;
import ci.digitalacademy.reservationimmobiliere.services.dto.*;
import ci.digitalacademy.reservationimmobiliere.services.mapper.VisitorReservationMapper;
import ci.digitalacademy.reservationimmobiliere.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorReservationServiceImpl implements VisitorReservationService {

    private final VisitorReservationRepository visitorReservationRepository;
    private final ResidenceService residenceService;
    private final VisitorReservationMapper visitorReservationMapper;
    private final OtherCustomerService otherCustomerService;
    private final UserService userService;
    @Override
    public VisitorReservationDTO save(VisitorReservationDTO visitorReservationDTO) {
        log.debug("Request to save Reservation : {}", visitorReservationDTO);
        Optional<OtherCustomerDTO> otherCustomerDTO = otherCustomerService.getById(visitorReservationDTO.getOtherCustomer().getIdPerson());
        Optional<ResidenceDTO> residenceDTO = residenceService.getResidenceById(visitorReservationDTO.getResidence().getId());
        if (otherCustomerDTO.isPresent() && residenceDTO.isPresent()) {
            visitorReservationDTO.setOtherCustomer(otherCustomerDTO.get());
            visitorReservationDTO.setResidence(residenceDTO.get());
            BigDecimal totalAmount = calculateTotalAmount(
                    visitorReservationDTO.getResidence().getId(),
                    visitorReservationDTO.getStartDate(),
                    visitorReservationDTO.getEndDate()
            );
            visitorReservationDTO.setTotalAmount(totalAmount);
        }
        VisitorReservation visitorReservation = visitorReservationMapper.toEntity(visitorReservationDTO);
        visitorReservation = visitorReservationRepository.save(visitorReservation);
        return visitorReservationMapper.fromEntity(visitorReservation);
    }


    @Override
    public Optional<VisitorReservationDTO> getById(Long id) {
        log.debug("Request to get Reservation by id : {}", id);
        return visitorReservationRepository.findById(id).map(visitorReservation -> {
            return visitorReservationMapper.fromEntity(visitorReservation);
        });
    }

    @Override
    public Optional<VisitorReservationDTO> getBySlug(String slug) {
        log.debug("Request to get Reservation by slug : {}", slug);
        return visitorReservationRepository.findBySlug(slug).map(visitorReservation -> {
            return visitorReservationMapper.fromEntity(visitorReservation);
        });
    }

    @Override
    public VisitorReservationDTO saveReservation(VisitorReservationDTO visitorReservationDTO) {
        log.debug("Request to save Reservation : {}", visitorReservationDTO);
        final String slug = SlugifyUtils.generate(visitorReservationDTO.getOtherCustomer().getLastName());
        visitorReservationDTO.setSlug(slug);
        return save(visitorReservationDTO);
    }

    @Override
    public List<VisitorReservationDTO> getAll() {
        log.debug("Request to get all Reservations");
        return visitorReservationRepository.findAll().stream().map(visitorReservation -> {
            return visitorReservationMapper.fromEntity(visitorReservation);
        }).toList();
    }

    public BigDecimal calculateTotalAmount(Long residenceId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début doit être antérieure ou égale à la date de fin");
        }
        ResidenceDTO residence = residenceService.getResidenceById(residenceId)
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
        List<VisitorReservationDTO> existingReservations = visitorReservationRepository.findByResidenceId(residenceId);

        for (VisitorReservationDTO reservation : existingReservations) {
            if (dateRangesOverlap(startDate, endDate, reservation.getStartDate(), reservation.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public VisitorReservationDTO saveReservationForOtherCustomer(RequestReservationOtherCustomerDTO requestReservationOtherCustomerDTO) {
        log.debug("Request to save Reservation for other customer : {}", requestReservationOtherCustomerDTO);
        UserDTO userDTO = userService.getByEmail(requestReservationOtherCustomerDTO.getEmail());
        if (Objects.nonNull(userDTO)) {
            throw new IllegalArgumentException("L'utilisateur existe déjà avec cet email");
        }
        OtherCustomerDTO otherCustomerDTO = null;
        otherCustomerDTO = otherCustomerService.getByEmail(requestReservationOtherCustomerDTO.getEmail());
        if (Objects.nonNull(otherCustomerDTO)) {
            if (!otherCustomerDTO.getPhoneNumber().equals(requestReservationOtherCustomerDTO.getPhoneNumber())){
                throw new IllegalArgumentException("L'email et le numéro de téléphone est déjà utilisé");
            }

        }else {
                otherCustomerDTO = new OtherCustomerDTO();
                otherCustomerDTO.setFirstName(requestReservationOtherCustomerDTO.getFirstName());
                otherCustomerDTO.setLastName(requestReservationOtherCustomerDTO.getLastName());
                otherCustomerDTO.setEmail(requestReservationOtherCustomerDTO.getEmail());
                otherCustomerDTO.setPhoneNumber(requestReservationOtherCustomerDTO.getPhoneNumber());
                otherCustomerDTO = otherCustomerService.save(otherCustomerDTO);
            }
        ResidenceDTO residenceDTO = residenceService.getResidenceById(requestReservationOtherCustomerDTO.getResidenceId()).orElseThrow(()->new IllegalArgumentException("Residence not found"));
        VisitorReservationDTO visitorReservationDTO = new VisitorReservationDTO();
        visitorReservationDTO.setOtherCustomer(otherCustomerDTO);
        visitorReservationDTO.setResidence(residenceDTO);
        visitorReservationDTO.setStartDate(requestReservationOtherCustomerDTO.getStartDate());
        visitorReservationDTO.setEndDate(requestReservationOtherCustomerDTO.getEndDate());
        visitorReservationDTO.setTotalAmount(calculateTotalAmount(requestReservationOtherCustomerDTO.getResidenceId(), requestReservationOtherCustomerDTO.getStartDate(), requestReservationOtherCustomerDTO.getEndDate()));
        return saveReservation(visitorReservationDTO);

    }

    private boolean dateRangesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
}
