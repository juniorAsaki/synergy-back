package ci.digitalacademy.reservationimmobiliere.services.impl;

import ci.digitalacademy.reservationimmobiliere.Repository.ResidenceRepository;
import ci.digitalacademy.reservationimmobiliere.Repository.VisitorReservationRepository;
import ci.digitalacademy.reservationimmobiliere.models.Residence;
import ci.digitalacademy.reservationimmobiliere.models.VisitorReservation;
import ci.digitalacademy.reservationimmobiliere.services.OtherCustomerService;
import ci.digitalacademy.reservationimmobiliere.services.ResidenceService;
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
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorReservationServiceImpl implements VisitorReservationService {

    private final VisitorReservationRepository visitorReservationRepository;
    private final ResidenceService residenceService;
    private final VisitorReservationMapper visitorReservationMapper;
    private final OtherCustomerService otherCustomerService;
    private final ResidenceRepository residenceRepository;
    @Override
    public VisitorVeservationDTO save(VisitorVeservationDTO visitorReservationDTO) {
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
    public Optional<VisitorVeservationDTO> getById(Long id) {
        log.debug("Request to get Reservation by id : {}", id);
        return visitorReservationRepository.findById(id).map(visitorReservation -> {
            return visitorReservationMapper.fromEntity(visitorReservation);
        });
    }

    @Override
    public Optional<VisitorVeservationDTO> getBySlug(String slug) {
        log.debug("Request to get Reservation by slug : {}", slug);
        return visitorReservationRepository.findBySlug(slug).map(visitorReservation -> {
            return visitorReservationMapper.fromEntity(visitorReservation);
        });
    }

    @Override
    public VisitorVeservationDTO saveReservation(VisitorVeservationDTO visitorReservationDTO) {
        log.debug("Request to save Reservation : {}", visitorReservationDTO);
        final String slug = SlugifyUtils.generate(visitorReservationDTO.getOtherCustomer().getLastName());
        visitorReservationDTO.setSlug(slug);
        return save(visitorReservationDTO);
    }

    @Override
    public List<VisitorVeservationDTO> getAll() {
        log.debug("Request to get all Reservations");
        return visitorReservationRepository.findAll().stream().map(visitorReservation -> {
            return visitorReservationMapper.fromEntity(visitorReservation);
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
        List<VisitorVeservationDTO> existingReservations = visitorReservationRepository.findByResidenceId(residenceId);

        for (VisitorVeservationDTO reservation : existingReservations) {
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
