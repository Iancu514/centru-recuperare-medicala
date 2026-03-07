package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.centrurecuperare.model.Salon;
import ro.upt.ac.centrurecuperare.service.SalonService;

import java.util.List;

@Controller
@RequestMapping("/saloane")
@RequiredArgsConstructor
public class SalonViewController {

    private final SalonService salonService;

    //Pagina cu lista de saloane
    @GetMapping
    public String listaSaloane(Model model){
        List<Salon> saloane = salonService.getAllSaloane();
        model.addAttribute("saloane", saloane);
        model.addAttribute("totalSaloane", saloane.size());
        model.addAttribute("saloaneDdisponibile",
                saloane.stream().filter(Salon::getDisponibil).count());
        return "saloane/lista";
    }

    //Pagina formular pentru salon on
    @GetMapping("/nou")
    public String formularSalonNou(Model model){
        model.addAttribute("salon", new Salon());
        model.addAttribute("titlu", "Aduaga salon nou");
        return "saloane/formular";
    }

    //Salveaz salon nou
    @PostMapping("/salveaza")
    public String salveazaSalon(@ModelAttribute Salon salon,
                                RedirectAttributes redirectAttributes){
        try{
            salonService.saveSalon(salon);
            redirectAttributes.addFlashAttribute("mesajSuccesc," +
                    "Salon adaugat cu succes!");
            return "redirect:/saloane";
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
            return "redirect:/saloane/nou";
        }
    }

    //Pagina formular pentru editare salon
    @GetMapping("/editeaza/{id}")
    public String formularEditareSalon(@PathVariable Long id, Model model,
                                       RedirectAttributes redirectAttributes){
        return salonService.getSalonById(id)
                .map(salon -> {
                    model.addAttribute("salon", salon);
                    model.addAttribute("titlu", "Editează salon");
                    return "saloane/formular";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mesajEroare",
                            "Salonul nu a fost găsit!");
                    return "redirect:/saloane";
                });
    }

    //Stergere salon
    @GetMapping("/sterge/{id}")
    public String stergeSalon(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            salonService.deleteSalon(id);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Salon șters cu succes!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
        }
        return "redirect:/saloane";
    }
}
