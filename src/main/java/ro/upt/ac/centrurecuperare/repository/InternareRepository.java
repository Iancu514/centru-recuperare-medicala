package ro.upt.ac.centrurecuperare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.model.Salon;

import java.util.List;

@Repository
public interface InternareRepository extends JpaRepository<Internare, Long> {

    //Gaseste internari dupa pacient
    List<Internare> findByPacient(Pacient pacient);

    //Gaseste internari dupa salon
    List<Internare> findBySalon(Salon salon);

    //Gaseste internari dupa status
    List<Internare> findByStatus(Internare.StatusInternare status);

    //Gaseste internari dupa prioritate
    List<Internare> findByPrioritate(Internare.Prioritate prioritate);

    //Gaseste internari in asteptare sortate dupa prioritate
    @Query("SELECT i FROM Internare i WHERE i.status = 'IN_ASTEPTARE' ORDER BY i.prioritate ASC, i.dataInternare ASC")
    List<Internare> findInternariInAsteptare();

    //Gaseste internari active fara salon alocat
    @Query("SELECT i FROM Internare i WHERE i.status IN ('APROBATA', 'IN_ASTEPTARE') AND i.salon IS NULL")
    List<Internare> findInternariNealocate();

    //Numara internari dupa status
    long countByStatus(Internare.StatusInternare status);

    //Verifica daca un pacient are internare activa
    @Query("SELECT COUNT(i) > 0 FROM Internare i WHERE i.pacient = :pacient AND i.status = 'ACTIVA'")
    boolean pacientAreInternareActiva(Pacient pacient);
}
