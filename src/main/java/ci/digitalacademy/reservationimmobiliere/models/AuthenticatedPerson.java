package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authenticated_person")
@Inheritance(strategy = InheritanceType.JOINED)
public class AuthenticatedPerson extends Person{

    @OneToOne
    private User user;

    @OneToOne
    private Address address;

}
