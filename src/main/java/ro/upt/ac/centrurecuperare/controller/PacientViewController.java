package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.service.PacientService;

import java.util.List;

@Controller
@RequestMapping("/pacienti")
@RequiredArgsConstructor
public class PacientViewController {

    private final PacientService pacientService;

    //Pagina cu lista de pacienti
    @GetMapping
    public String listaPacienti(Model model){
        List<Pacient> pacienti = pacientService.getAllPacienti();
        model.addAttribute("pacienti", pacienti);
        model.addAttribute("totalPacienti", pacienti.size());
        return "pacienti/lista";
    }

    //Pagina formular pentru pacient nou
    @GetMapping("/nou")
    public String formularPacientNou(Model model){
        model.addAttribute("pacient", new Pacient());
        model.addAttribute("titlu", "Adauga Pacient Nou");
        return "pacienti/formular";
    }

    //Salvez pacient nou
    @PostMapping("/salveaza")
    public String salveazaPacient(@ModelAttribute Pacient pacient,
                                  RedirectAttributes redirectAttributes){
        try {
            pacientService.savePacient(pacient);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Pacient adaugat cu succes!");
            return "redirect:/pacienti";
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
            return "redirect:/pacienti/nou";
        }
    }

    //Pagina formular pentru editare pacient
    @GetMapping("/editeaza/{id}")
    public String formularEditarePacient(@PathVariable Long id, Model model,
                                         RedirectAttributes redirectAttributes){
        return pacientService.getPacientById(id)
                .map(pacient -> {
                    model.addAttribute("pacient", pacient);
                    model.addAttribute("titlu", "Editeaza Pacient");
                    return "pacienti/formular";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mesajEroare",
                            "Pacientul nu a fost gasit!");
                    return "redirect:/pacienti";
                });
    }

    //Sterge pacient
    @GetMapping("/sterge/{id}")
    public String stergePacient(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            pacientService.deletePacient(id);
            redirectAttributes.addFlashAttribute("mesajSucces",
                    "Pacient sters cu succes!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mesajEroare", e.getMessage());
        }
        return "redirect:/pacienti";
    }
}
