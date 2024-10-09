package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "residence")
public class Residence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private String description;
    private boolean available;
    private String slug;

    @OneToMany(mappedBy = "residence")
    private List<Review> reviews;

    @OneToOne
    private Address address;

    @ManyToOne
    private Owner owner;

    @OneToMany
    private List<Image> images;
}
