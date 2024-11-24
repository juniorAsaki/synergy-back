package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal price;
    private String description;
    private String name;
    private Integer rooms;
    private Integer showers;
    private Integer diningRoom;
    private Integer terrace;
    private Integer lounges;
    private boolean wifi;
    private boolean parking;
    private boolean catering;
    private boolean cleaning;
    private boolean available;
    private String longitude;
    private String latitude;

    @Column(unique = true)
    private String slug;

    @OneToMany(mappedBy = "residence", fetch = FetchType.EAGER)
    private List<Review> reviews;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Address address;

    @ManyToOne
    private Owner owner;

    @OneToMany(mappedBy = "residence",fetch = FetchType.EAGER )
    private List<PictureResidence> images;
}
