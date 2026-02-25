package ro.upt.ac.centrurecuperare.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.upt.ac.centrurecuperare.model.TipTerapie;
import ro.upt.ac.centrurecuperare.repository.TipTerapieRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TipTerapieService {

    private final TipTerapieRepository tipTerapieRepository;

    public List<TipTerapie> getAllTipuriTerapie(){
        return tipTerapieRepository.findAll();
    }

    public Optional<TipTerapie> getTipTerapieById(Long id) {
        return tipTerapieRepository.findById(id);
    }

    public List<TipTerapie> getTipuriTerapieActive() {
        return tipTerapieRepository.findByActiv(true);
    }

    public TipTerapie saveTipTerapie(TipTerapie tipTerapie){
        //Validare: denumire unica
        if (tipTerapie.getId() == null &&
                tipTerapieRepository.findByDenumire(tipTerapie.getDenumire()).isPresent()) {
            throw new IllegalArgumentException("Există deja un tip de terapie cu denumirea: " +
                    tipTerapie.getDenumire());
        }

        //Validare durata valida
        if (tipTerapie.getDurataMinute() == null || tipTerapie.getDurataMinute() < 1) {
            throw new IllegalArgumentException("Durata trebuie să fie cel puțin 1 minut");
        }

        //Validare pret valid
        if (tipTerapie.getPret() == null || tipTerapie.getPret() < 0) {
            throw new IllegalArgumentException("Prețul trebuie să fie pozitiv");
        }

        return tipTerapieRepository.save(tipTerapie);
    }

    public void deleteTipTerapie(Long id){
        if(!tipTerapieRepository.existsById(id)) {
            throw new IllegalArgumentException("Nu există tip terapie cu ID-ul: " + id);
        }
        tipTerapieRepository.deleteById(id);
    }

    public long countTipuriTerapie() {
        return tipTerapieRepository.count();
    }
}
