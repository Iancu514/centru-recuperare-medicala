package ro.upt.ac.centrurecuperare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.upt.ac.centrurecuperare.model.Terapeut;
import ro.upt.ac.centrurecuperare.repository.TerapeutRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TerapeutService {

    private final TerapeutRepository terapeutRepository;

    public List<Terapeut> getAllTerapeuti() {
        return terapeutRepository.findAll();
    }

    public Optional<Terapeut> getTerapeutById(Long id) {
        return terapeutRepository.findById(id);
    }

    public List<Terapeut> getTerapeutiActivi() {
        return terapeutRepository.findByActiv(true);
    }

    public List<Terapeut> getTerapeutiBySpecializare(Terapeut.Specializare specializare) {
        return terapeutRepository.findBySpecializare(specializare);
    }

    public Terapeut saveTerapeut(Terapeut terapeut) {
        // Validari
        if (terapeut.getTelefon() == null || terapeut.getTelefon().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefonul este obligatoriu");
        }

        if (terapeut.getAniExperienta() != null && terapeut.getAniExperienta() < 0) {
            throw new IllegalArgumentException("Anii de experiență trebuie să fie pozitivi");
        }

        // Verifica email duplicat (doar pentru terapeuti noi)
        if (terapeut.getId() == null && terapeutRepository.existsByEmail(terapeut.getEmail())) {
            throw new IllegalArgumentException("Există deja un terapeut cu email-ul " + terapeut.getEmail());
        }

        return terapeutRepository.save(terapeut);
    }

    public void deleteTerapeut(Long id){
        if (!terapeutRepository.existsById(id)) {
            throw new IllegalArgumentException("Nu există terapeut cu ID-ul: " + id);
        }
        terapeutRepository.deleteById(id);
    }

    public long countTerapeuti() {
        return terapeutRepository.count();
    }

    public long countTerapeutiActivi() {
        return terapeutRepository.countByActiv(true);
    }
}
