package ro.upt.ac.centrurecuperare.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.upt.ac.centrurecuperare.dto.RezultatAlocare;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.model.Salon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceOptimizareTest {

    @Mock
    private InternareService internareService;

    @Mock
    private SalonService salonService;

    @InjectMocks
    private ServiceOptimizare serviceOptimizare;

    private List<Internare> internariTest;
    private List<Salon> saloaneTest;
    private Pacient pacientTest;

    @BeforeEach
    void setUp() {
        //Date de test
        pacientTest = new Pacient();
        pacientTest.setId(1L);
        pacientTest.setNume("Popescu");
        pacientTest.setPrenume("Ion");
        pacientTest.setCnp("1234567890123");

        //saloane de test
        saloaneTest = new ArrayList<>();

        Salon salon1 = new Salon();
        salon1.setId(1L);
        salon1.setNumar("101");
        salon1.setCapacitate(2);
        salon1.setDisponibil(true);
        saloaneTest.add(salon1);

        Salon salon2 = new Salon();
        salon2.setId(2L);
        salon2.setNumar("102");
        salon2.setCapacitate(4);
        salon2.setDisponibil(true);
        saloaneTest.add(salon2);

        //internari de test
        internariTest = new ArrayList<>();

        Internare internare1 = new Internare();
        internare1.setId(1L);
        internare1.setPacient(pacientTest);
        internare1.setDataInternare(LocalDate.now());
        internare1.setDurataEstimata(7);
        internare1.setPrioritate(Internare.Prioritate.URGENTA);
        internare1.setStatus(Internare.StatusInternare.IN_ASTEPTARE);
        internariTest.add(internare1);

        Internare internare2 = new Internare();
        internare2.setId(2L);
        internare2.setPacient(pacientTest);
        internare2.setDataInternare(LocalDate.now());
        internare2.setDurataEstimata(10);
        internare2.setPrioritate(Internare.Prioritate.NORMALA);
        internare2.setStatus(Internare.StatusInternare.IN_ASTEPTARE);
        internariTest.add(internare2);
    }

    @Test
    void testFirstFit_Succes() {
        //Arange
        when(salonService.getSaloaneDisponibile()).thenReturn(saloaneTest);
        when(internareService.alocaSalon(anyLong(), any(Salon.class))).thenReturn(internariTest.get(0));

        //Act
        RezultatAlocare rezultat = serviceOptimizare.firstFit(internariTest.get(0));

        //Assert
        assertNotNull(rezultat);
        assertTrue(rezultat.isSuccess());
        assertNotNull(rezultat.getSalonAlocat());
        assertEquals("101", rezultat.getSalonAlocat().getNumar());
        verify(internareService, times(1)).alocaSalon(anyLong(), any(Salon.class));
    }

    @Test
    void testFirstFit_NoRoomsAvailable() {
        //Arrange
        when(salonService.getSaloaneDisponibile()).thenReturn(new ArrayList<>());

        //Act
        RezultatAlocare rezultat = serviceOptimizare.firstFit(internariTest.get(0));

        //Assert
        assertNotNull(rezultat);
        assertFalse(rezultat.isSuccess());
        assertNull(rezultat.getSalonAlocat());
        assertTrue(rezultat.getMotiv().contains("Nu exista saloane disponibile"));
        verify(internareService, never()).alocaSalon(anyLong(), any(Salon.class));
    }

    @Test
    void testBestFit_SelectsSmallestRoom() {

        when(salonService.getSaloaneDisponibile()).thenReturn(saloaneTest);
        when(internareService.alocaSalon(anyLong(), any(Salon.class))).thenReturn(internariTest.get(0));

        RezultatAlocare rezultat = serviceOptimizare.bestFit(internariTest.get(0));

        assertNotNull(rezultat);
        assertTrue(rezultat.isSuccess());
        assertNotNull(rezultat.getSalonAlocat());
        // ar trebui sa selecteze cea mai mica camera(101 cu capacitatea 2)
        assertEquals("101", rezultat.getSalonAlocat().getNumar());
        assertEquals(2, rezultat.getSalonAlocat().getCapacitate());
    }

    @Test
    void testPriorityBasedAllocation_RescpectsOrder() {

        when(internareService.getInternariNealocate()).thenReturn(internariTest);
        when(salonService.getSaloaneDisponibile()).thenReturn(saloaneTest);
        when(internareService.alocaSalon(anyLong(), any(Salon.class)))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);
                    return internariTest.stream()
                            .filter(i -> i.getId().equals(id))
                            .findFirst()
                            .orElse(null);
                });

        List<RezultatAlocare> rezultate = serviceOptimizare.priorityBasedAllocation();

        assertNotNull(rezultate);
        assertEquals(2, rezultate.size());

        // primul rezultat ar trebui sa fie prioritate URGENTA
        assertEquals(Internare.Prioritate.URGENTA,
                rezultate.get(0).getInternare().getPrioritate());

        // al doilea rezultat ar trebui sa fie prioritate NORMALA
        assertEquals(Internare.Prioritate.NORMALA,
                rezultate.get(1).getInternare().getPrioritate());
    }

    @Test
    void testGetStatisticiAlocare_CalculatesCorrectly() {

        List<RezultatAlocare> rezultate = new ArrayList<>();

        // 3 alocari cu succes
        rezultate.add(RezultatAlocare.succes(internariTest.get(0), saloaneTest.get(0), "Success"));
        rezultate.add(RezultatAlocare.succes(internariTest.get(1), saloaneTest.get(1), "Success"));
        rezultate.add(RezultatAlocare.esec(internariTest.get(0), "Failed"));

        Map<String, Object> statistici = serviceOptimizare.getStatisticiAlocare(rezultate);

        assertNotNull(statistici);
        assertEquals(3, statistici.get("totalInternari"));
        assertEquals(2, statistici.get("alocariReusit"));
        assertEquals(1, statistici.get("alocariEsuate"));

        double rataSucces = (double) statistici.get("rataSucces");
        assertEquals(66.66, rataSucces, 0.01); // 2/3 * 100 = 66.66%
    }

    @Test
    void testGetStatisticiAlocare_EmptyList() {

        List<RezultatAlocare> rezultate = new ArrayList<>();

        Map<String, Object> statistici = serviceOptimizare.getStatisticiAlocare(rezultate);

        assertNotNull(statistici);
        assertEquals(0, statistici.get("totalInternari"));
        assertEquals(0, statistici.get("alocariReusit"));
        assertEquals(0, statistici.get("alocariEsuate"));
        assertEquals(0.0, statistici.get("rataSucces"));
    }

    @Test
    void testGreedyMaxOcupare_AllocatesAll() {

        when(internareService.getInternariNealocate()).thenReturn(internariTest);
        when(salonService.getSaloaneDisponibile()).thenReturn(saloaneTest);
        when(internareService.alocaSalon(anyLong(), any(Salon.class)))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);
                    return internariTest.stream()
                            .filter(i -> i.getId().equals(id))
                            .findFirst()
                            .orElse(null);
                });

        List<RezultatAlocare> rezultate = serviceOptimizare.greedyMaxOcupare();

        assertNotNull(rezultate);
        assertEquals(2, rezultate.size());

        long successful = rezultate.stream()
                .filter(RezultatAlocare::isSuccess)
                .count();
        assertEquals(2, successful);
    }
}
