package ro.upt.ac.centrurecuperare.service;

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
        // Validari
        if (internare.getPacient() == null) {
            throw new IllegalArgumentException("Pacientul este obligatoriu");
        }

        if (internare.getDataInternare() == null) {
            throw new IllegalArgumentException("Data internării este obligatorie");
        }

        if (internare.getDurataEstimata() == null || internare.getDurataEstimata() < 1) {
            throw new IllegalArgumentException("Durata estimată trebuie să fie mai mare sau egală cu 1 zi");
        }

        // Verifica daca pacientul are deja o internare activa
        if (internare.getId() == null && internareRepository.pacientAreInternareActiva(internare.getPacient())) {
            throw new IllegalArgumentException("Pacientul " + internare.getPacient().getNume() +
                    " are deja o internare activă");
        }

        return internareRepository.save(internare);
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