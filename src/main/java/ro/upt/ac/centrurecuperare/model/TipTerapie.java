package ro.upt.ac.centrurecuperare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipuri_terapie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipTerapie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String denumire; // ex: Fizioterapie, Hidroterapie, Kinetoterapie

    @Column(length = 500)
    private String descriere;

    @Column(nullable = false)
    private Integer durataMinute; // durata unei sedinte in minute

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipResursa resurseNecesare;

    @Column(nullable = false)
    private Double pret;  // pretul unei sedinte (in lei)

    @Column
    private Boolean activ = true; //daca terapia e disponibila

    @Column
    private Integer capacitateMaximaSimultana = 1; //cati pacienti pot fi tratati simultan

    public enum TipResursa{
        SALA_STANDARD,   // Sala obisnuita de terapie
        PISCINA,         // Necesita piscina geotermala
        ECHIPAMENT_SPECIAL,  // Aparatura specializata
        EXTERIOR,        // Activitati in aer liber
        GRUP             // Terapie de grup (sala mare)
    }
}
