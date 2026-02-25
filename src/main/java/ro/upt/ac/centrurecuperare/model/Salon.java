package ro.upt.ac.centrurecuperare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numar;

    @Column(nullable = false)
    private Integer capacitate; //nr de paturi

    @Column(nullable = false)
    private Integer etaj; // 0 (parter), 1, 2, etc

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipSalon tip;

    @Column
    private Boolean disponibil = true; //daca e disponibil pt internari

    @Column(length = 500)
    private String observatii; //dotari speciale, etc

    public enum TipSalon{
        STANDARD,     // Salon obisnuit
        VIP,          // Salon premium cu dotari superioare
        TERAPIE,      // Salon special pentru terapii (ex. cu acces la piscina)
        RECUPERARE    // Salon specializat pentru recuperare intensiva
    }

}
