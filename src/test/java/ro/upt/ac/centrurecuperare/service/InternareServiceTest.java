package ro.upt.ac.centrurecuperare.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.model.Salon;
import ro.upt.ac.centrurecuperare.repository.InternareRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InternareServiceTest {

    @Mock
    private InternareRepository internareRepository;

    @InjectMocks
    private InternareService internareService;

    private Pacient pacientTest;
    private Salon salonTest;
    private Internare internareTest;

    @BeforeEach
    void setUp() {
        // pacient de test
        pacientTest = new Pacient();
        pacientTest.setId(1L);
        pacientTest.setNume("Popescu");
        pacientTest.setPrenume("Ion");
        pacientTest.setCnp("1234567890123");

        // salon de test
        salonTest = new Salon();
        salonTest.setId(1L);
        salonTest.setNumar("101");
        salonTest.setCapacitate(2);
        salonTest.setDisponibil(true);

        // internare de test
        internareTest = new Internare();
        internareTest.setId(1L);
        internareTest.setPacient(pacientTest);
        internareTest.setDataInternare(LocalDate.now());
        internareTest.setDurataEstimata(7);
        internareTest.setPrioritate(Internare.Prioritate.NORMALA);
        internareTest.setStatus(Internare.StatusInternare.IN_ASTEPTARE);
    }

    @Test
    void testSaveInternare_Success() {

        when(internareRepository.save(any(Internare.class))).thenReturn(internareTest);

        Internare saved = internareService.saveInternare(internareTest);

        assertNotNull(saved);
        assertEquals(internareTest.getId(), saved.getId());
        verify(internareRepository, times(1)).save(any(Internare.class));
    }

    @Test
    void testSaveInternare_PacientNull_ThrowsException() {

        internareTest.setPacient(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> internareService.saveInternare(internareTest)
        );

        assertEquals("Pacientul este obligatoriu", exception.getMessage());
        verify(internareRepository, never()).save(any(Internare.class));
    }

    @Test
    void testSaveInternare_DataInternareNull_ThrowsException() {

        internareTest.setDataInternare(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> internareService.saveInternare(internareTest)
        );

        assertEquals("Data internării este obligatorie", exception.getMessage());
        verify(internareRepository, never()).save(any(Internare.class));
    }

    @Test
    void testSaveInternare_DurataInvalida_ThrowsException() {

        internareTest.setDurataEstimata(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> internareService.saveInternare(internareTest)
        );

        assertTrue(exception.getMessage().contains("Durata estimată"));
        verify(internareRepository, never()).save(any(Internare.class));
    }

    @Test
    void testSaveInternare_PacientAreInternareActiva_ThrowsException() {

        internareTest.setId(null);
        when(internareRepository.pacientAreInternareActiva(any(Pacient.class))).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> internareService.saveInternare(internareTest)
        );

        // Print debug
        System.out.println("Mesaj primit: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("activă"));
        verify(internareRepository, never()).save(any(Internare.class));
    }

    @Test
    void testAlocaSalon_Success() {

        when(internareRepository.findById(1L)).thenReturn(Optional.of(internareTest));
        when(internareRepository.save(any(Internare.class))).thenReturn(internareTest);

        Internare result = internareService.alocaSalon(1L, salonTest);

        assertNotNull(result);
        assertEquals(salonTest, result.getSalon());
        assertEquals(Internare.StatusInternare.ACTIVA, result.getStatus());
        verify(internareRepository, times(1)).save(any(Internare.class));
    }

    @Test
    void testAlocaSalon_InternareNotFound_ThrowsEception() {
        when(internareRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> internareService.alocaSalon(999L, salonTest)
        );

        assertTrue(exception.getMessage().contains("999"));
        verify(internareRepository, never()).save(any(Internare.class));
    }

    @Test
    void testFinalizeazaInternare_Success() {

        internareTest.setStatus(Internare.StatusInternare.ACTIVA);
        when(internareRepository.findById(1L)).thenReturn(Optional.of(internareTest));
        when(internareRepository.save(any(Internare.class))).thenReturn(internareTest);

        Internare result = internareService.finalizeazaInternare(1L);

        assertNotNull(result);
        assertEquals(Internare.StatusInternare.FINALIZATA, result.getStatus());
        assertNotNull(result.getDataExternare());
        assertEquals(LocalDate.now(), result.getDataExternare());
        verify(internareRepository, times(1)).save(any(Internare.class));
    }

    @Test
    void testDeleteInternare_Success() {

        when(internareRepository.existsById(1L)).thenReturn(true);
        doNothing().when(internareRepository).deleteById(1L);

        assertDoesNotThrow(() -> internareService.deleteInternare(1L));
        verify(internareRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInternare_NotFound_ThrowsException() {

        when(internareRepository.existsById(999L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> internareService.deleteInternare(999L)
        );

        assertTrue(exception.getMessage().contains("Nu există internare"));
        verify(internareRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCountInternari() {

        when(internareRepository.count()).thenReturn(5L);

        long count = internareService.countInternari();

        assertEquals(5L, count);
        verify(internareRepository, times(1)).count();
    }

    @Test
    void testCountInternariByStatus() {

        when(internareRepository.countByStatus(Internare.StatusInternare.ACTIVA)).thenReturn(3L);

        long count = internareService.countInternariByStatus(Internare.StatusInternare.ACTIVA);

        assertEquals(3L, count);
        verify(internareRepository, times(1)).countByStatus(Internare.StatusInternare.ACTIVA);
    }
}
