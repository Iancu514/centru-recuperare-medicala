package ro.upt.ac.centrurecuperare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.model.Salon;
import ro.upt.ac.centrurecuperare.repository.InternareRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InternareService {

    private static final Logger logger = LoggerFactory.getLogger(InternareService.class);

    private final InternareRepository internareRepository;

    public List<Internare> getAllInternari() {
        return internareRepository.findAll();
    }

    public Optional<Internare> getInternareById(Long id) {
        return internareRepository.findById(id);
    }

    public List<Internare> getInternariByPacient(Pacient pacient) {
        return internareRepository.findByPacient(pacient);
    }

    public List<Internare> getInternariInAsteptare() {
        return internareRepository.findInternariInAsteptare();
    }

    public List<Internare> getInternariNealocate() {
        return internareRepository.findInternariNealocate();
    }

    public List<Internare> getInternariByStatus(Internare.StatusInternare status) {
        return internareRepository.findByStatus(status);
    }

    public Internare saveInternare(Internare internare) {
        logger.debug("Incercare salvare internare pentru pacient: {}",
                internare.getPacient() != null ? internare.getPacient().getNume() : "NULL");

        // Validari
        if (internare.getPacient() == null) {
            logger.error("Validare esuata: Pacient este null");
            throw new IllegalArgumentException("Pacientul este obligatoriu");
        }

        if (internare.getDataInternare() == null) {
            logger.error("Validare esuata: Data internare este null pentru pacient {}",
                    internare.getPacient().getNume());
            throw new IllegalArgumentException("Data internării este obligatorie");
        }

        if (internare.getDurataEstimata() == null || internare.getDurataEstimata() < 1) {
            logger.error("Validare esuata: Durata invalida {} pentru pacient {}",
                    internare.getDurataEstimata(), internare.getPacient().getNume());
            throw new IllegalArgumentException("Durata estimată trebuie să fie mai mare sau egală cu 1 zi");
        }

        // Verifica daca pacientul are deja o internare activa
        if (internare.getId() == null && internareRepository.pacientAreInternareActiva(internare.getPacient())) {
            logger.warn("Validare esuata: Pacient {} are deja internare activa",
                    internare.getPacient().getNume());
            throw new IllegalArgumentException("Pacientul " + internare.getPacient().getNume() +
                    " are deja o internare activă");
        }

        Internare saved = internareRepository.save(internare);
        logger.info("Salvare reusita internare ID={} pentru pacient {} (Prioritate: {}, Durata: {} zile)",
                saved.getId(),
                saved.getPacient().getNume(),
                saved.getPrioritate(),
                saved.getDurataEstimata());

        return saved;
    }

    //Aloca un salon pentru o internare

    public Internare alocaSalon(Long internareId, Salon salon) {
        Internare internare = internareRepository.findById(internareId)
                .orElseThrow(() -> new IllegalArgumentException("Internarea cu ID " + internareId + " nu a fost găsită"));

        internare.setSalon(salon);
        internare.setStatus(Internare.StatusInternare.ACTIVA);

        return internareRepository.save(internare);
    }

    //Finalizeaza o internare

    public Internare finalizeazaInternare(Long internareId) {
        Internare internare = internareRepository.findById(internareId)
                .orElseThrow(() -> new IllegalArgumentException("Internare nu a fost găsită"));

        internare.setStatus(Internare.StatusInternare.FINALIZATA);
        internare.setDataExternare(LocalDate.now());

        return internareRepository.save(internare);
    }

    public void deleteInternare(Long id) {
        if (!internareRepository.existsById(id)) {
            throw new IllegalArgumentException("Nu există internare cu ID-ul: " + id);
        }
        internareRepository.deleteById(id);
    }

    public long countInternari() {
        return internareRepository.count();
    }

    public long countInternariByStatus(Internare.StatusInternare status) {
        return internareRepository.countByStatus(status);
    }
}