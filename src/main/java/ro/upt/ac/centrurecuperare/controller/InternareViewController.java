package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.model.Salon;
import ro.upt.ac.centrurecuperare.service.InternareService;
import ro.upt.ac.centrurecuperare.service.PacientService;
import ro.upt.ac.centrurecuperare.service.SalonService;

import java.util.List;

@Controller
@RequestMapping("/internari")
@RequiredArgsConstructor
public class InternareViewController {

    private final InternareService internareService;
    private final PacientService pacientService;
    private final SalonService salonService;

    // Pagina principala - lista internari
    @GetMapping
    public String listaInternari(@RequestParam(required = false) String status, Model model) {
        List<Internare> internari;

        if(status != null && !status.isEmpty()) {
            // filtrare dupa status
            internari = internareService.getInternariByStatus(
                    Internare.StatusInternare.valueOf(status));
        } else {
            // toate internarie
            internari = internareService.getAllInternari();
        }

        model.addAttribute("internari", internari);
        model.addAttribute("totalInternari", internari.size());
        model.addAttribute("statusFiltru", status);

        // Statistici rapide
        model.addAttribute("nealocate",
                internareService.countInternariByStatus(Internare.StatusInternare.IN_ASTEPTARE) +
                            internareService.countInternariByStatus(Internare.StatusInternare.APROBATA));
        model.addAttribute("active",
                internareService.countInternariByStatus(Internare.StatusInternare.ACTIVA));
        model.addAttribute("finalizate",
                internareService.countInternariByStatus(Internare.StatusInternare.FINALIZATA));

        return "internari/lista";
    }

    // Formular pt internare noua
    @GetMapping("/nou")
    public String formularInternareNoua(Model model) {
        model.addAttribute("internare", new Internare());
        model.addAttribute("titlu", "Cerere internare Nouă");

        // Liste pt dropdown-uri
        model.addAttribute("pacienti", pacientService.getAllPacienti());
        model.addAttribute("saloane", salonService.getSaloaneDisponibile());

        return "internari/formular";
    }

    // Salvare internare
    @PostMapping("/salveaza")
    public String salveazaInternare(@ModelAttribute Internare internare,
                                    @RequestParam Long pacientId,
                                    @RequestParam(required = false) Long salonId,
                                    RedirectAttributes redirectAttributes) {
        try {
            // setez pacientul
            Pacient pacient = pacientService.getPacientById(pacientId)
                    .orElseThrow(() -> new IllegalArgumentException("Pacient invalid"));
            internare.setPacient(pacient);

            // setez salonul daca e selectat
            if(salonId != null) {
                Salon salon = salonService.getSalonById(salonId)
                        .orElseThrow(() -> new IllegalArgumentException("Salonu invalid"));
                internare.setSalon(salon);
                internare.setStatus(Internare.StatusInternare.ACTIVA);
            } else {
                internare.setStatus(Internare.StatusInternare.IN_ASTEPTARE);
            }

            internareService.saveInternare(internare);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Cerere de internare înregistrată cu succes!");
            return "redirect:/internari";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
            return "redirect:/internari/nou";
        }
    }

    // Formular editare internare
    @GetMapping("/editeaza/{id}")
    public String formularEditareInternare(@PathVariable Long id, Model model,
                                           RedirectAttributes redirectAttributes) {
        return internareService.getInternareById(id)
                .map(internare -> {
                    model.addAttribute("internare", internare);
                    model.addAttribute("titlu", "Editeaza internare");
                    model.addAttribute("pacienti", pacientService.getAllPacienti());
                    model.addAttribute("saloane", salonService.getAllSaloane());
                    return "internari/formular";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mesajEroare",
                            "Internarea nu a fost găsită!");
                    return "redirect:/internari";
                });
    }

    // Detalii internare
    @GetMapping("/detalii/{id}")
    public String detaliiInternare(@PathVariable Long id, Model model,
                                   RedirectAttributes redirectAttributes) {
        return internareService.getInternareById(id)
                .map(internare -> {
                    model.addAttribute("internare", internare);
                    return "internari/detalii";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mesajEroare",
                            "Internarea nu a fost găsită!");
                    return "redirect:/internari";
                });
    }

    // Finalizare internare
    @GetMapping("/finalizeaza/{id}")
    public String finalizeazaInternare(@PathVariable Long id, Model model,
                                       RedirectAttributes redirectAttributes) {
        try {
            internareService.finalizeazaInternare(id);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Internare finalizată cu succes!");
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
        }
        return "redirect:/internari";
    }

    // Stergere internare
    @GetMapping("/sterge/{id}")
    public String stergeInternare(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            internareService.deleteInternare(id);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Internare ștearsă cu succes!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
        }
        return "redirect:/internari";
    }
}
