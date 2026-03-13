package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.centrurecuperare.dto.RezultatAlocare;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.service.InternareService;
import ro.upt.ac.centrurecuperare.service.SalonService;
import ro.upt.ac.centrurecuperare.service.ServiceOptimizare;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/optimizare")
@RequiredArgsConstructor
public class OptimizareController {

    private final ServiceOptimizare serviceOptimizare;
    private final InternareService internareService;
    private final SalonService salonService;

    // Pagina principala de optimizare
    @GetMapping
    public String paginaOptimizare(Model model) {
        //obtin internari nealocate
        List<Internare> internariNealocate = internareService.getInternariNealocate();

        //obtin saloane disponibile
        long saloaneDisponibile = salonService.countSaloaneDdisponibile();

        model.addAttribute("internariNealocate", internariNealocate);
        model.addAttribute("numarInternari", internariNealocate.size());
        model.addAttribute("saloaneDisponibile", saloaneDisponibile);

        return "optimizare/dashboard";
    }

    // Rulez algoritm Priority-Based
    @PostMapping("/ruleaza-priority")
    @Transactional
    public String ruleazaPriority(RedirectAttributes redirectAttributes) {
        try {
            List<RezultatAlocare> rezultate = serviceOptimizare.priorityBasedAllocation();
            Map<String, Object> statistici = serviceOptimizare.getStatisticiAlocare(rezultate);

            redirectAttributes.addFlashAttribute("rezultate", rezultate);
            redirectAttributes.addFlashAttribute("statistici", statistici);
            redirectAttributes.addFlashAttribute("algoritm", "Priority-Based Allocation");
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Algoritm Priority-Based executat cu succes!");

            return "redirect:/optimizare/rezultate";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mesajEroare",
                    "Eroare la rularea algoritmului: " + e.getMessage());
            return "redirect:/optimizare";
        }
    }

    // Rulez algoritm Greedy
    @PostMapping("/ruleaza-greedy")
    @Transactional
    public String ruleazaGreedy(RedirectAttributes redirectAttributes) {
        try {
            List<RezultatAlocare> rezultate = serviceOptimizare.greedyMaxOcupare();
            Map<String, Object> statistici = serviceOptimizare.getStatisticiAlocare(rezultate);

            redirectAttributes.addFlashAttribute("rezultate", rezultate);
            redirectAttributes.addFlashAttribute("statistici", statistici);
            redirectAttributes.addFlashAttribute("algoritm", "Greedy - Maximizare Ocupare");
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Algoritm Greedy executat cu succes!");

            return "redirect:/optimizare/rezultate";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mesajEroare",
                    "Eroare la rularea algoritmului: " + e.getMessage());
            return "redirect:/optimizare";
        }
    }

    // Pagina cu rezultatele alocarii
    @GetMapping("/rezultate")
    public String paginaRezultate(Model model) {
        // rezultatele sunt transmise prin Flash Attributes
        return "optimizare/rezultate";
    }

}
