package ro.upt.ac.centrurecuperare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.upt.ac.centrurecuperare.dto.RezultatAlocare;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Salon;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServiceOptimizare {

    private final InternareService internareService;
    private final SalonService salonService;

    // Aloritm 1: First Fit
    // Aloca pacientul in primul salon disponibil care are capacitate
    // Complexitate O(n), unde n = nr de saloane
    public RezultatAlocare firstFit(Internare internare) {
        log.info("Ruleaza aloritm First Fit pentru internarea ID: {}" + internare.getId());

        List<Salon> saloaneDisponibile = salonService.getSaloaneDisponibile();

        //Parcurg saloanele in ordine
        for (Salon salon : saloaneDisponibile) {
            //Verifica daca salonul are capacitate
            if(areLoc(salon)) {
                //Aloca
                internareService.alocaSalon(internare.getId(), salon);
                log.info("FIRST FIT: Alocat in salon {} (capacitate: {})",
                        salon.getNumar(), salon.getCapacitate());
                return RezultatAlocare.succes(internare, salon,
                        "First Fit: Primul salon disponibil");
            }
        }

        log.warn("FIRST FIT: Nu s-a gasit salon disponibil");
        return RezultatAlocare.esec(internare, "Nu exista saloane disponibile");
    }

    /**
     * ALGORITM 2: BEST FIT
     * Aloca pacientul in salonul cel mai potrivit (cel mai mic care are loc)
     *
     * Complexitate: O(n log n) - sortare + parcurgere
     */
    public RezultatAlocare bestFit(Internare internare) {
        log.info("Rulează algoritm BEST FIT pentru internarea ID: {}", internare.getId());

        List<Salon> saloaneDisponibile = salonService.getSaloaneDisponibile();

        //Filtreaza doar saloanele care au loc
        List<Salon> saloaneCuLoc = saloaneDisponibile.stream()
                .filter(this::areLoc)
                .collect(Collectors.toList());

        if (saloaneCuLoc.isEmpty()) {
            log.warn("BEST FIT: Nu există saloane cu loc disponibil");
            return RezultatAlocare.esec(internare, "Nu există saloane cu loc");
        }

        //Sorteaza dupa capacitate (crescator) cel mai mic salon potrivit
        saloaneCuLoc.sort(Comparator.comparing(Salon::getCapacitate));

        // Ia primul (cel mai mic care are loc)
        Salon bestSalon = saloaneCuLoc.get(0);
        internareService.alocaSalon(internare.getId(), bestSalon);

        log.info("BEST FIT: Alocat in salon {} (capacitate: {}, waste minimizat)",
                bestSalon.getNumar(), bestSalon.getCapacitate());

        return RezultatAlocare.succes(internare, bestSalon,
                "Best Fit: Salonul cel mai potrivit (capacitate minima cu loc)");
    }

    /**
     * Algoritm 3: PRIORITY-BASED ALLOCATION
     * Aloca pacientii in functie de prioritate (URGENTA > RIDICATA > NORMALA)
     * Pentru fiecare nivel de prioritate, foloseste Best Fit
     *
     * Complexitate: O(m * n log n) unde m = nr internari, n = nr saloane
     */
    public List<RezultatAlocare> priorityBasedAllocation() {
        log.info("Ruleaza algoritm PRIORITY-BASED ALLOCATION");

        List<RezultatAlocare> rezultate = new ArrayList<>();

        //Obtin toate internarile nealocate, sortate dupa prioritate
        List<Internare> internari = internareService.getInternariNealocate();

        //Sortare dupa prioritate (Urgenta = 0, Ridicata = 1, Normala = 2, Scazuta = 3)
        internari.sort(Comparator.comparing(i -> i.getPrioritate().ordinal()));

        log.info("Procesare {} internări nealocate", internari.size());

        //Proceseaza fiecare internare in ordinea prioritatii
        for (Internare internare : internari) {
            RezultatAlocare rezultat = bestFit(internare);
            rezultate.add(rezultat);

            if(rezultat.isSuccess()) {
                log.info("Prioritate {}: Alocat pacient {} în salon {}",
                        internare.getPrioritate(),
                        internare.getPacient().getNume(),
                        rezultat.getSalonAlocat().getNumar());
            } else {
                log.warn("Prioritate {}: NU s-a putut aloca pacient {}",
                        internare.getPrioritate(),
                        internare.getPacient().getNume());
            }
        }

        return rezultate;
    }

    /**
     * Algoritm 4: GREEDY cu Maximizare Ocupare
     * Incearca sa maximizeze gradul de ocupare al saloanelor
     */
    public List<RezultatAlocare> greedyMaxOcupare() {
        log.info("Rulează algoritm GREEDY - Maximizare Ocupare");

        List<RezultatAlocare> rezultate = new ArrayList<>();
        List<Internare> internari = internareService.getInternariNealocate();

        // Pentru fiecare internare
        for (Internare internare : internari) {
            List<Salon> saloaneDisponibile = salonService.getSaloaneDisponibile();

            Salon bestSalon = null;
            double bestScore = -1;

            // Gasește salonul cu cel mai bun scor (ocupare maxima)
            for (Salon salon : saloaneDisponibile) {
                if (areLoc(salon)) {
                    // Scor = ocupare dupa alocare
                    double ocupareDupa = calculeazaOcupare(salon) + (1.0 / salon.getCapacitate());

                    if (ocupareDupa > bestScore) {
                        bestScore = ocupareDupa;
                        bestSalon = salon;
                    }
                }
            }

            if (bestSalon != null) {
                internareService.alocaSalon(internare.getId(), bestSalon);
                rezultate.add(RezultatAlocare.succes(internare, bestSalon,
                        "Greedy: Maximizare ocupare (" + String.format("%.1f%%", bestScore * 100) + ")"));
            } else {
                rezultate.add(RezultatAlocare.esec(internare, "Nu există saloane disponibile"));
            }
        }

        return rezultate;
    }


    // ==== METODE HELPER ====

    // Verifica daca un salon are loc
    private boolean areLoc(Salon salon){
        return salon.getDisponibil();
    }

    // Calculeaza gradul de ocupare al unui salon
    private double calculeazaOcupare(Salon salon){
        return 0.0;
    }

    //Statistici despre alocare
    public Map<String, Object> getStatisticiAlocare(List<RezultatAlocare> rezultate) {
        Map<String, Object> stats = new HashMap<>();

        long alocariReusit = rezultate.stream().filter(RezultatAlocare::isSuccess).count();
        long alocariEsuate = rezultate.stream().filter(r -> !r.isSuccess()).count();

        stats.put("totalInternari", rezultate.size());
        stats.put("alocariReusit", alocariReusit);
        stats.put("alocariEsuate", alocariEsuate);
        stats.put("rataSucces", rezultate.isEmpty() ? 0 :
                (double) alocariReusit / rezultate.size() * 100);

        return stats;
    }
}