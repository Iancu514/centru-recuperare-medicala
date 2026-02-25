package ro.upt.ac.centrurecuperare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.upt.ac.centrurecuperare.model.TipTerapie;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipTerapieRepository extends JpaRepository<TipTerapie, Long> {

    Optional<TipTerapie> findByDenumire(String denumire);

    List<TipTerapie> findByActiv(Boolean activ);

    List<TipTerapie> findByResurseNecesare(TipTerapie.TipResursa resursa);

    List<TipTerapie> findByDurataMinuteLessThanEqual(Integer durataMaxima);
}
