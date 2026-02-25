package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.service.PacientService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final PacientService pacientService;

    @GetMapping("/")
    public String dashboard(Model model) {
        List<Pacient> pacienti = pacientService.getAllPacienti();

        // Statistici generale
        long totalPacienti = pacienti.size();

        // Pacienti per status
        Map<Pacient.StatusPacient, Long> pacientiPerStatus = pacienti.stream()
                .collect(Collectors.groupingBy(Pacient::getStatus, Collectors.counting()));

        long pacientiActivi = pacientiPerStatus.getOrDefault(Pacient.StatusPacient.ACTIV, 0L);
        long pacientiInternati = pacientiPerStatus.getOrDefault(Pacient.StatusPacient.INTERNAT, 0L);
        long pacientiExternati = pacientiPerStatus.getOrDefault(Pacient.StatusPacient.EXTERNAT, 0L);

        // Adaug în model
        model.addAttribute("totalPacienti", totalPacienti);
        model.addAttribute("pacientiActivi", pacientiActivi);
        model.addAttribute("pacientiInternati", pacientiInternati);
        model.addAttribute("pacientiExternati", pacientiExternati);
        model.addAttribute("ultimiiPacienti", pacienti.stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .limit(5)
                .collect(Collectors.toList()));

        return "dashboard";
    }
}
