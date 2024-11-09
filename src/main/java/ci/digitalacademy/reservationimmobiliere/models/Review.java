package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
    private String note;
    private String slug;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Residence residence;
}
