package ci.digitalacademy.reservationimmobiliere.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OtherCustomer extends Person {

    @Column(nullable = false, unique = true)
    private String email;
}
