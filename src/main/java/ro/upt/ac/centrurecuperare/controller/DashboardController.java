package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.service.*;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final PacientService pacientService;
    private final SalonService salonService;
    private final TipTerapieService tipTerapieService;
    private final TerapeutService terapeutService;
    private final InternareService internareService;

    @GetMapping("/")
    public String dashboard(Model model) {
        // Statistici Pacienti
        model.addAttribute("totalPacienti", pacientService.countPacienti());
        model.addAttribute("pacientiActivi", pacientService.countPacientiActivi());
        model.addAttribute("pacientiInternati", pacientService.countPacientiInternati());
        model.addAttribute("pacientiExternati", pacientService.countPacientiExternati());


        // Statistici Saloane
        model.addAttribute("totalSaloane", salonService.countSaloane());
        model.addAttribute("saloaneDisponibile", salonService.countSaloaneDdisponibile());

        // Statistici Terapii
        model.addAttribute("totalTerapii", tipTerapieService.countTipuriTerapie());

        // Statistici Terapeuti
        model.addAttribute("totalTerapeuti", terapeutService.countTerapeuti());
        model.addAttribute("terapeutiActivi", terapeutService.countTerapeutiActivi());

        // Statistici Internari
        model.addAttribute("totalInternari", internareService.countInternari());
        model.addAttribute("internariInAsteptare",
                internareService.countInternariByStatus(Internare.StatusInternare.IN_ASTEPTARE) +
                        internareService.countInternariByStatus(Internare.StatusInternare.APROBATA));
        model.addAttribute("internariActive",
                internareService.countInternariByStatus(Internare.StatusInternare.ACTIVA));
        model.addAttribute("internariFinalizate",
                internareService.countInternariByStatus(Internare.StatusInternare.FINALIZATA));

        return "dashboard";
    }
}
