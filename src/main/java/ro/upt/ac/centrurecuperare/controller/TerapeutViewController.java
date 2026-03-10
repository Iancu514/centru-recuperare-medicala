package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.centrurecuperare.model.Terapeut;
import ro.upt.ac.centrurecuperare.service.TerapeutService;

import java.util.List;

@Controller
@RequestMapping("/terapeuti")
@RequiredArgsConstructor
public class TerapeutViewController {

    private final TerapeutService terapeutService;

    @GetMapping
    public String listaTerapeuti(Model model) {
        List<Terapeut> terapeuti = terapeutService.getAllTerapeuti();
        model.addAttribute("terapeuti", terapeuti);
        model.addAttribute("totalTerapeuti", terapeuti.size());
        model.addAttribute("terapeutiActivi",
                terapeuti.stream().filter(Terapeut::getActiv).count());
        return "terapeuti/lista";
    }

    @GetMapping("/nou")
    public String formularTerapeutNou(Model model) {
        model.addAttribute("terapeut", new Terapeut());
        model.addAttribute("titlu", "Adaugă Terapeut Nou");
        return "terapeuti/formular";
    }

    @PostMapping("/salveaza")
    public String salveazaTerapeut(@ModelAttribute Terapeut terapeut,
                                   RedirectAttributes redirectAttributes) {
        try {
            terapeutService.saveTerapeut(terapeut);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Terapeut adăugat cu succes!");
            return "redirect:/terapeuti";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
            return "redirect:/terapeuti/nou";
        }
    }

    @GetMapping("/editeaza/{id}")
    public String formularEditareTerapeut(@PathVariable Long id, Model model,
                                          RedirectAttributes redirectAttributes) {
        return terapeutService.getTerapeutById(id)
                .map(terapeut -> {
                    model.addAttribute("terapeut", terapeut);
                    model.addAttribute("titlu", "Editează Terapeut");
                    return "terapeuti/formular";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mesajEroare",
                            "Terapeutul nu a fost găsit!");
                    return "redirect:/terapeuti";
                });
    }

    @GetMapping("/sterge/{id}")
    public String stergeTerapeut(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            terapeutService.deleteTerapeut(id);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Terapeut șters cu succes!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
        }
        return "redirect:/terapeuti";
    }
}