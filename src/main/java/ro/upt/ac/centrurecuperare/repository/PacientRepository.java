package ro.upt.ac.centrurecuperare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.upt.ac.centrurecuperare.model.Pacient;

import java.util.Optional;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Long> {

    Optional<Pacient> findByCnp(String cnp);

    boolean existsByCnp(String cnp);
}
