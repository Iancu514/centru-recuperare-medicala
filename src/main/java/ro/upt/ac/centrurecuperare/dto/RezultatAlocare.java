package ro.upt.ac.centrurecuperare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Salon;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RezultatAlocare {

    private Internare internare;
    private Salon salonAlocat;
    private boolean success;
    private String motiv; // Motivul alocarii sau esecului

    // Constructor pentru succes
    public static RezultatAlocare succes(Internare internare, Salon salon, String motiv) {
        return new RezultatAlocare(internare, salon, true, motiv);
    }

    // Constructor pentru esec
    public static RezultatAlocare esec(Internare internare, String motiv) {
        return new RezultatAlocare(internare, null, false, motiv);
    }
}