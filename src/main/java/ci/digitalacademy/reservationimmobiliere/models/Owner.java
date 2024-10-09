package ci.digitalacademy.reservationimmobiliere.models;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Owner extends Person {

    @OneToOne
    private User user;


}
