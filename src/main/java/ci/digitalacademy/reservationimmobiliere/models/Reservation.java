package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
@Inheritance(strategy = InheritanceType.JOINED)
public class Reservation  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    private BigDecimal totalAmount;
    private String slug;

    @OneToOne
    private Residence residence;

}
