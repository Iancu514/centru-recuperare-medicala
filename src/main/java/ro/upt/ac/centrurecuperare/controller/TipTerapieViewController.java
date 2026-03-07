package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.centrurecuperare.model.TipTerapie;
import ro.upt.ac.centrurecuperare.service.TipTerapieService;

import java.util.List;

@Controller
@RequestMapping("/terapii")
@RequiredArgsConstructor
public class TipTerapieViewController {

    private final TipTerapieService tipTerapieService;

    // Pagina cu lista de terapii
    @GetMapping
    public String listaTerapii(Model model) {
        List<TipTerapie> terapii = tipTerapieService.getAllTipuriTerapie();
        model.addAttribute("terapii", terapii);
        model.addAttribute("totalTerapii", terapii.size());
        model.addAttribute("terapiiActive",
                terapii.stream().filter(TipTerapie::getActiv).count());
        return "terapii/lista";
    }

    // Pagina formular pentru terapie noua
    @GetMapping("/nou")
    public String formularTerapieNoua(Model model) {
        model.addAttribute("terapie", new TipTerapie());
        model.addAttribute("titlu", "Adaugă Tip Terapie Nou");
        return "terapii/formular";
    }

    // Salveaza terapie noua
    @PostMapping("/salveaza")
    public String salveazaTerapie(@ModelAttribute TipTerapie terapie,
                                  RedirectAttributes redirectAttributes) {
        try {
            tipTerapieService.saveTipTerapie(terapie);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Terapie adaugata cu succes!");
            return "redirect:/terapii";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
            return "redirect:/terapii/nou";
        }
    }

    //Pagina formular pentru editare terapie
    @GetMapping("/editeaza/{id}")
    public String formularEditareTerapie(@PathVariable Long id, Model model,
                                         RedirectAttributes redirectAttributes) {
        return tipTerapieService.getTipTerapieById(id)
                .map(terapie -> {
                    model.addAttribute("terapie", terapie);
                    model.addAttribute("titlu", "Editează Tip Terapie");
                    return "terapii/formular";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mesajEroare",
                            "Terapia nu a fost gasită!");
                    return "redirect:/terapii";
                });
    }

    // Sterge terapie
    @GetMapping("/sterge/{id}")
    public String stergeTerapie(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            tipTerapieService.deleteTipTerapie(id);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Terapie ștearsă cu succes!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
        }
        return "redirect:/terapii";
    }
}
