package ro.upt.ac.centrurecuperare.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.repository.PacientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PacientService {

    private final PacientRepository pacientRepository;

    //Returneaza toti pacientii
    public List<Pacient> getAllPacienti(){
        return pacientRepository.findAll();
    }

    //Caut pacient dupa ID
    public Optional<Pacient> getPacientById(Long id){
        return pacientRepository.findById(id);
    }

    //Cauta pacient dupa CNP
    public Optional<Pacient> getPacientByCnp(String cnp){
        return  pacientRepository.findByCnp(cnp);
    }

    //Salveaza pacient nou sau actualizeaza unul deja existent, cu validare CNP
    public Pacient savePacient(Pacient pacient){
        if(pacient.getId() == null && pacientRepository.existsByCnp(pacient.getCnp())){
            throw new IllegalArgumentException("Exista deja un pacient cu CNP-ul: " + pacient.getCnp());
        }

        //Validare CNP, care trebuie sa aiba 13 caractere
        if(pacient.getCnp() == null || pacient.getCnp().length() != 13){
            throw new IllegalArgumentException("CNP-ul trebuie sa aiba exact 13 caractere");
        }

        return pacientRepository.save(pacient);
    }

    //Stergere pacient dupa ID
    public void deletePacient(Long id){
        if (!pacientRepository.existsById(id)){
            throw new IllegalArgumentException("Nu exista pacient cu ID-ul: " + id);
        }
        pacientRepository.deleteById(id);
    }

    //Verificare daca exista vreun pacient cu un anumit CNP
    public boolean existByCnp(String cnp){
        return pacientRepository.existsByCnp(cnp);
    }

    //Numara toti pacientii
    public long countPacienti(){
        return pacientRepository.count();
    }
}
