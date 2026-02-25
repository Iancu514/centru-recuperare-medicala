package ro.upt.ac.centrurecuperare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pacienti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pacient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nume;

    @Column(nullable = false, length = 100)
    private String prenume;

    @Column(nullable = false, unique = true, length = 13)
    private String cnp;

    @Column(nullable = false)
    private LocalDate dataNasterii;

    @Column(length = 15)
    private String telefon;

    @Column(length = 200)
    private String adresa;

    @Column(length = 500)
    private String diagnostic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPacient status = StatusPacient.ACTIV;

    @Column
    private LocalDate dataInregistrare = LocalDate.now();

    // Enum pentru status
    public enum StatusPacient {
        ACTIV,
        INTERNAT,
        EXTERNAT,
        INACTIV
    }
}
