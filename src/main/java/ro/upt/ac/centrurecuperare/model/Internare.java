package ro.upt.ac.centrurecuperare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "internari")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Internare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relatie Many-to-One cu Pacient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacient_id", nullable = false)
    private Pacient pacient;

    //Relatie Many-to-One cu Salon (poate fi null la inceput)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @Column(nullable = false)
    private LocalDate dataInternare;

    @Column
    private LocalDate dataExternare;

    @Column(nullable = false)
    private Integer durataEstimata; // in zile

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInternare status = StatusInternare.IN_ASTEPTARE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioritate prioritate = Prioritate.NORMALA;

    @Column(length = 1000)
    private String diagnostic;

    @Column(length = 1000)
    private String obiectiveTratament;

    @Column(length = 500)
    private String observatii;

    public enum StatusInternare {
        IN_ASTEPTARE,   //Cerere primita, in asteptare
        APROBATA,       //Aprobata, asteapta alocare salon
        ACTIVA,         //Pacient internat activ
        FINALIZATA,     //Internare finalizata cu succes
        ANULATA         //Cerere anulata
    }

    public enum Prioritate {
        URGENTA,   //Urgenta medicala
        RIDICATA,  //Prioritate ridicata
        NORMALA,   //Prioritate normala
        SCAZUTA,   //Poate astepta
    }
}