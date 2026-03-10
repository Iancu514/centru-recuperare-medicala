package ro.upt.ac.centrurecuperare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.upt.ac.centrurecuperare.model.Terapeut;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerapeutRepository extends JpaRepository<Terapeut, Long> {

    Optional<Terapeut> findByEmail(String email);

    List<Terapeut> findByActiv(Boolean activ);

    List<Terapeut> findBySpecializare(Terapeut.Specializare specializare);

    boolean existsByEmail(String email);

    long countByActiv(Boolean activ);
}
