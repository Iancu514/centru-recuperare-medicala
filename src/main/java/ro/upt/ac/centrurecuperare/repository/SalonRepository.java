package ro.upt.ac.centrurecuperare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.upt.ac.centrurecuperare.model.Salon;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {

    Optional<Salon> findByNumar(String numar);

    List<Salon> findByDisponibil(Boolean disponibil);

    List<Salon> findByEtaj(Integer etaj);

    List<Salon> findByTip(Salon.TipSalon tip);

    @Query("SELECT s FROM Salon s WHERE s.disponibil = true AND s.capacitate >= :capacitateMinima")
    List<Salon> findSaloaneDdisponibileByCapacitate(Integer capacitateMinima);

    long countByDisponibil(Boolean disponibil);
}
