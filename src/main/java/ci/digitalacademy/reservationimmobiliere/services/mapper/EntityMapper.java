package ci.digitalacademy.reservationimmobiliere.services.mapper;

public interface EntityMapper<D, E>{

    D fromEntity(E entity);
    E toEntity(D dto);
}
