package ro.upt.ac.centrurecuperare.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.upt.ac.centrurecuperare.model.Terapeut;
import ro.upt.ac.centrurecuperare.repository.TerapeutRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TerapeutServiceTest {

    @Mock
    private TerapeutRepository terapeutRepository;

    @InjectMocks
    private TerapeutService terapeutService;

    private Terapeut terapeutTest;

    @BeforeEach
    void setUp() {
        terapeutTest = new Terapeut();
        terapeutTest.setId(1L);
        terapeutTest.setNume("Popescu");
        terapeutTest.setPrenume("Ana");
        terapeutTest.setEmail("ana.popescu@test.ro");
        terapeutTest.setTelefon("0721234567");
        terapeutTest.setSpecializare(Terapeut.Specializare.FIZIOTERAPEUT);
        terapeutTest.setAniExperienta(5);
        terapeutTest.setDataAngajare(LocalDate.now());
        terapeutTest.setActiv(true);
    }

    @Test
    void testSaveTerapeut_Success() {

        when(terapeutRepository.save(any(Terapeut.class))).thenReturn(terapeutTest);

        Terapeut saved = terapeutService.saveTerapeut(terapeutTest);

        assertNotNull(saved);
        assertEquals(terapeutTest.getEmail(), saved.getEmail());
        verify(terapeutRepository, times(1)).save(any(Terapeut.class));
    }

    @Test
    void testSaveTerapeut_DuplicateEmail_ThrowsException() {

        terapeutTest.setId(null); // New terapeut
        when(terapeutRepository.existsByEmail(anyString())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> terapeutService.saveTerapeut(terapeutTest)
        );

        assertTrue(exception.getMessage().contains("Există deja un terapeut cu email-ul"));
        verify(terapeutRepository, never()).save(any(Terapeut.class));
    }

    @Test
    void testSaveTerapeut_TelefonNull_ThrowsException() {

        terapeutTest.setTelefon(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> terapeutService.saveTerapeut(terapeutTest)
        );

        assertTrue(exception.getMessage().contains("Telefonul este obligatoriu"));
        verify(terapeutRepository, never()).save(any(Terapeut.class));
    }

    @Test
    void testSaveTerapeut_TelefonEmpty_ThrowsException() {
        // Arrange
        terapeutTest.setTelefon("   ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> terapeutService.saveTerapeut(terapeutTest)
        );

        assertTrue(exception.getMessage().contains("Telefonul este obligatoriu"));
        verify(terapeutRepository, never()).save(any(Terapeut.class));
    }

    @Test
    void testSaveTerapeut_AniExperientaNegative_ThrowsException() {
        // Arrange
        terapeutTest.setAniExperienta(-1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> terapeutService.saveTerapeut(terapeutTest)
        );

        //Print pt debug
        System.out.println("Mesaj primit: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("pozitivi"));
        verify(terapeutRepository, never()).save(any(Terapeut.class));
    }

    @Test
    void testGetAllTerapeuti() {

        List<Terapeut> terapeuti = new ArrayList<>();
        terapeuti.add(terapeutTest);
        when(terapeutRepository.findAll()).thenReturn(terapeuti);

        List<Terapeut> result = terapeutService.getAllTerapeuti();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(terapeutTest.getEmail(), result.get(0).getEmail());
        verify(terapeutRepository, times(1)).findAll();
    }

    @Test
    void testGetTerapeutById_Found() {

        when(terapeutRepository.findById(1L)).thenReturn(Optional.of(terapeutTest));

        Optional<Terapeut> result = terapeutService.getTerapeutById(1L);

        assertTrue(result.isPresent());
        assertEquals(terapeutTest.getEmail(), result.get().getEmail());
        verify(terapeutRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTerapeutById_NotFound() {

        when(terapeutRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Terapeut> result = terapeutService.getTerapeutById(999L);

        assertFalse(result.isPresent());
        verify(terapeutRepository, times(1)).findById(999L);
    }

    @Test
    void testGetTerapeutiActivi() {

        List<Terapeut> terapeutiActivi = new ArrayList<>();
        terapeutiActivi.add(terapeutTest);
        when(terapeutRepository.findByActiv(true)).thenReturn(terapeutiActivi);

        List<Terapeut> result = terapeutService.getTerapeutiActivi();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getActiv());
        verify(terapeutRepository, times(1)).findByActiv(true);
    }

    @Test
    void testGetTerapeutiBySpecializare() {

        List<Terapeut> terapeuti = new ArrayList<>();
        terapeuti.add(terapeutTest);
        when(terapeutRepository.findBySpecializare(Terapeut.Specializare.FIZIOTERAPEUT))
                .thenReturn(terapeuti);

        List<Terapeut> result = terapeutService.getTerapeutiBySpecializare(
                Terapeut.Specializare.FIZIOTERAPEUT);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Terapeut.Specializare.FIZIOTERAPEUT, result.get(0).getSpecializare());
        verify(terapeutRepository, times(1))
                .findBySpecializare(Terapeut.Specializare.FIZIOTERAPEUT);
    }

    @Test
    void testDeleteTerapeut_Success() {

        when(terapeutRepository.existsById(1L)).thenReturn(true);
        doNothing().when(terapeutRepository).deleteById(1L);

        assertDoesNotThrow(() -> terapeutService.deleteTerapeut(1L));
        verify(terapeutRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTerapeut_NotFound_ThrowsException() {

        when(terapeutRepository.existsById(999L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> terapeutService.deleteTerapeut(999L)
        );

        assertTrue(exception.getMessage().contains("Nu există terapeut cu ID-ul"));
        verify(terapeutRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCountTerapeuti() {

        when(terapeutRepository.count()).thenReturn(10L);

        long count = terapeutService.countTerapeuti();

        assertEquals(10L, count);
        verify(terapeutRepository, times(1)).count();
    }

    @Test
    void testCountTerapeutiActivi() {

        when(terapeutRepository.countByActiv(true)).thenReturn(8L);

        long count = terapeutService.countTerapeutiActivi();

        assertEquals(8L, count);
        verify(terapeutRepository, times(1)).countByActiv(true);
    }
}

