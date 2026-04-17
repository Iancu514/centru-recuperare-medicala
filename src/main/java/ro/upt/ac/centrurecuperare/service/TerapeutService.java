package ro.upt.ac.centrurecuperare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(TerapeutService.class);

    private final TerapeutRepository terapeutRepository;

    public List<Terapeut> getAllTerapeuti() {
        logger.debug("Obtinere lista completa terapeuti");
        List<Terapeut> terapeuti = terapeutRepository.findAll();
        logger.info("Gasit {} terapeuti in total", terapeuti.size());
        return terapeuti;
    }

    public Optional<Terapeut> getTerapeutById(Long id) {
        logger.debug("Cautare terapeut cu ID={}", id);
        Optional<Terapeut> terapeut = terapeutRepository.findById(id);
        if (terapeut.isPresent()) {
            logger.debug("Terapeut gasit: {} {}", terapeut.get().getNume(), terapeut.get().getPrenume());
        } else {
            logger.warn("Terapeut cu ID={} nu a fost gasit", id);
        }
        return terapeut;
    }

    public List<Terapeut> getTerapeutiActivi() {
        logger.debug("Obtinere terapeuti activi");
        List<Terapeut> activi = terapeutRepository.findByActiv(true);
        logger.info("Gasit {} terapeuti activi", activi.size());
        return activi;
    }

    public List<Terapeut> getTerapeutiBySpecializare(Terapeut.Specializare specializare) {
        logger.debug("Cautare terapeuti cu specializare: {}", specializare);
        List<Terapeut> terapeuti = terapeutRepository.findBySpecializare(specializare);
        logger.info("Gasit {} terapeuti cu specializare {}", terapeuti.size(), specializare);
        return terapeuti;
    }

    public Terapeut saveTerapeut(Terapeut terapeut) {
        logger.debug("Incercare salvare terapeut: {} {}",
                terapeut.getNume(), terapeut.getPrenume());

        // Validari
        if (terapeut.getTelefon() == null || terapeut.getTelefon().trim().isEmpty()) {
            logger.error("Validare esuata: Telefon obligatoriu pentru terapeut {} {}",
                    terapeut.getNume(), terapeut.getPrenume());
            throw new IllegalArgumentException("Telefonul este obligatoriu");
        }

        if (terapeut.getAniExperienta() != null && terapeut.getAniExperienta() < 0) {
            logger.error("Validare esuata: Ani experienta invalizi {} pentru terapeut {} {}",
                    terapeut.getAniExperienta(), terapeut.getNume(), terapeut.getPrenume());
            throw new IllegalArgumentException("Anii de experiență trebuie să fie pozitivi");
        }

        // Verifica email duplicat (doar pentru terapeuti noi)
        if (terapeut.getId() == null && terapeutRepository.existsByEmail(terapeut.getEmail())) {
            logger.warn("Validare esuata: Email {} exista deja", terapeut.getEmail());
            throw new IllegalArgumentException("Există deja un terapeut cu email-ul " + terapeut.getEmail());
        }

        Terapeut saved = terapeutRepository.save(terapeut);
        logger.info("Salvare reusita terapeut ID={}, Nume={} {}, Specializare={}",
                saved.getId(),
                saved.getNume(),
                saved.getPrenume(),
                saved.getSpecializare());

        return saved;
    }

    public void deleteTerapeut(Long id) {
        logger.debug("Incercare stergere terapeut cu ID={}", id);

        if (!terapeutRepository.existsById(id)) {
            logger.error("Stergere esuata: Nu exista terapeut cu ID={}", id);
            throw new IllegalArgumentException("Nu există terapeut cu ID-ul: " + id);
        }

        terapeutRepository.deleteById(id);
        logger.info("Terapeut cu ID={} sters cu succes", id);
    }

    public long countTerapeuti() {
        long count = terapeutRepository.count();
        logger.debug("Total terapeuti: {}", count);
        return count;
    }

    public long countTerapeutiActivi() {
        long count = terapeutRepository.countByActiv(true);
        logger.debug("Total terapeuti activi: {}", count);
        return count;
    }
}