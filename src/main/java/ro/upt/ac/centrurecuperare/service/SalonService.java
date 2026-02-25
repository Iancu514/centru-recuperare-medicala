package ro.upt.ac.centrurecuperare.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.upt.ac.centrurecuperare.model.Salon;
import ro.upt.ac.centrurecuperare.repository.SalonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SalonService {

    private final SalonRepository salonRepository;

    public List<Salon> getAllSaloane(){
        return salonRepository.findAll();
    }

    public Optional<Salon> getSalonById(Long id){
        return salonRepository.findById(id);
    }

    public Optional<Salon> getSalonByNumar(String numar){
        return salonRepository.findByNumar(numar);
    }

    public List<Salon> getSaloaneDisponibile(){
        return salonRepository.findByDisponibil(true);
    }

    public List<Salon> getSalonByEtaj(Integer etaj){
        return salonRepository.findByEtaj(etaj);
    }

    public Salon saveSalon(Salon salon){
        // Validare: numar salon unic
        if (salon.getId() == null && salonRepository.findByNumar(salon.getNumar()).isPresent()){
            throw new IllegalArgumentException("Există deja un salon cu numărul: " + salon.getNumar());
        }

        // Validare: capacitate valida
        if (salon.getCapacitate() == null || salon.getCapacitate() < 1){
            throw new IllegalArgumentException("Capacitatea salonului trebuie să fie cel puțin 1");
        }

        return salonRepository.save(salon);
    }

    public void deleteSalon(Long id) {
        if (!salonRepository.existsById(id)) {
            throw new IllegalArgumentException("Nu există salon cu ID-ul: " + id);
        }
        salonRepository.deleteById(id);
    }

    public long countSaloane() {
        return salonRepository.count();
    }

    public long countSaloaneDdisponibile(){
        return salonRepository.countByDisponibil(true);
    }
}
