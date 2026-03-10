package ro.upt.ac.centrurecuperare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="terapeutii")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Terapeut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nume;

    @Column(nullable = false, length = 100)
    private String prenume;

    @Column(unique = true, length = 13, nullable = true)
    private String cnp;

    @Column(nullable = false, length = 15)
    private String telefon;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specializare specializare;

    @Column(nullable = false)
    private Integer aniExperienta = 0;

    @Column
    private LocalDate dataAngajare;

    @Column
    private Boolean activ = true;

    @Column(length = 500)
    private String observatii;

    public enum Specializare {
        FIZIOTERAPEUT,    //fizioterapie generala
        KINETOTERAPEUT,   //kinetoterapie
        MASEUR,           //masaj terapeutic
        INSTRUCTOR_HIDRO, //hidroterapie/aquagym
        RECUPERARE_MEDICALA, //medicina de recuperare
        NURSING           //asistent medical
    }
}
