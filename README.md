# Centru Recuperare Medicală - Sistem de Management

Aplicație web pentru gestionarea unui centru de recuperare medicală cu algoritmi avansați de optimizare pentru alocarea pacienților în saloane.

## Cuprins

- [Despre Proiect](#despre-proiect)
- [Funcționalități Principale](#funcționalități-principale)
- [Tehnologii Utilizate](#tehnologii-utilizate)
- [Algoritmi de Optimizare](#algoritmi-de-optimizare)
- [Instalare și Configurare](#instalare-și-configurare)
- [Rulare Aplicație](#rulare-aplicație)
- [Structura Bazei de Date](#structura-bazei-de-date)
- [Testing](#testing)
- [Utilizare](#utilizare)

## Despre Proiect

Acest sistem a fost dezvoltat ca proiect de licență pentru Facultatea de Automatică și Calculatoare, Universitatea Politehnica Timișoara. Aplicația oferă soluții complete pentru gestionarea activităților dintr-un centru de recuperare medicală, incluzând funcționalitate CRUD pentru pacienți, saloane, terapii și terapeuți, precum și algoritmi avansați pentru optimizarea alocării pacienților în saloane.

Sistemul implementează patru algoritmi de alocare cu complexități diferite, fiecare optimizând criterii specifice: prioritatea medicală, eficiența economică sau minimizarea spațiului neutilizat. Aplicația include, de asemenea, un sistem complet de raportare cu export în format Excel și PDF.

### Obiective

Proiectul își propune să demonstreze aplicarea practică a algoritmilor de optimizare în domeniul medical, oferind o soluție funcțională care poate fi adaptată pentru utilizare reală în instituții medicale. Pe lângă aspectul algoritmic, aplicația pune accent pe calitatea codului, testare automată, securitate și logging avansat.

## Funcționalități Principale

### Autentificare și Securitate
Sistemul utilizează Spring Security pentru autentificare și autorizare. Parolele sunt criptate folosind BCrypt, iar aplicația implementează protecție CSRF. Toate paginile aplicației necesită autentificare, cu excepția paginii de login.

### Gestionare Pacienți
Modulul de gestionare pacienți permite adăugarea, editarea și ștergerea pacienților din sistem. Fiecare pacient este identificat prin CNP și conține informații complete: nume, prenume, dată nașterii, adresă, telefon și status. Lista de pacienți poate fi exportată în format Excel pentru arhivare sau raportare.

### Gestionare Saloane
Sistemul gestionează 40 de saloane distribuite pe 4 etaje, cu capacități variabile între 1 și 5 paturi. Fiecare salon are un număr unic, etaj, capacitate, status de disponibilitate și observații. Interfața permite filtrarea saloaneor după disponibilitate și vizualizarea în timp real a ocupării.

### Gestionare Terapii și Terapeuți
Aplicația menține evidența a 8 tipuri de terapii disponibile (kinetoterapie, electroterapie, hidroterapie, etc.) și a 12 terapeuți cu specializări diferite. Pentru fiecare terapeut se păstrează informații despre specializare, experiență și disponibilitate.

### Gestionare Internări
Modulul central al aplicației gestionează internările pacienților. O internare conține referințe către pacient și salon (dacă este alocat), data internării, durata estimată, prioritatea (URGENTA, RIDICATA, NORMALA, SCAZUTA) și statusul (IN_ASTEPTARE, ACTIVA, FINALIZATA). Sistemul permite atât alocare manuală cât și alocare automată folosind algoritmii de optimizare.

### Optimizare Alocări
Dashboard-ul de optimizare oferă acces la cei doi algoritmi principali de alocare: Priority-Based Allocation (care respectă prioritățile medicale) și Greedy Max Ocupare (care maximizează eficiența economică). După rulare, sistemul afișează rezultatele detaliate cu statistici complete: număr total de internări procesate, alocări reușite, alocări eșuate și rata de succes.

### Export și Raportare
Aplicația permite exportul datelor în două formate:
- Excel (.xlsx) pentru pacienți și internări
- PDF (.pdf) pentru internări și rezultate optimizare

Fișierele generate includ timestamp în nume și sunt formatate profesional pentru imprimare sau arhivare.

## Tehnologii Utilizate

### Backend
- **Java 17** - limbaj de programare
- **Spring Boot 4.0.3** - framework pentru dezvoltare aplicații
    - Spring Data JPA - pentru persistență date
    - Spring Security - pentru autentificare și autorizare
    - Spring Web - pentru controlere REST și MVC
- **Hibernate 7.2.4** - ORM (Object-Relational Mapping)
- **MySQL 9.6.0** - sistem de gestiune baze de date
- **Lombok** - reducere cod boilerplate
- **SLF4J cu Logback** - logging

### Frontend
- **Thymeleaf 3.1.3** - motor de template-uri server-side
- **Bootstrap 5.3.0** - framework CSS pentru interfață responsive
- **Bootstrap Icons 1.11.0** - set de iconițe

### Testing
- **JUnit 5 (6.0.3)** - framework pentru teste unitare
- **Mockito 5.20.0** - framework pentru mock objects
- **Spring Boot Test** - utilități pentru testare

### Export
- **Apache POI 5.2.5** - generare fișiere Excel
- **iText7 7.2.5** - generare fișiere PDF

### Build și Deploy
- **Maven** - management dependențe și build
- **Git** - control versiuni

## Algoritmi de Optimizare

Sistemul implementează patru algoritmi de alocare, fiecare cu caracteristici specifice și cazuri de utilizare diferite.

### First Fit - O(n)

Algoritmul First Fit parcurge lista de saloane disponibile și alocă pacientul în primul salon găsit care satisface cerințele (disponibil și cu capacitate suficientă). Este cel mai rapid algoritm, dar nu optimizează utilizarea spațiului.

```java
public RezultatAlocare firstFit(Internare internare) {
    List<Salon> saloaneDisponibile = salonService.getSaloaneDisponibile();
    
    for (Salon salon : saloaneDisponibile) {
        if (salon.areCapacitate()) {
            return RezultatAlocare.succes(internare, salon, "Alocare First Fit");
        }
    }
    
    return RezultatAlocare.esec(internare, "Nu exista saloane disponibile");
}
```

**Complexitate:** O(n) unde n = numărul de saloane disponibile

**Utilizare:** Este folosit ca metodă helper de către algoritmul Greedy Max Ocupare.

### Best Fit - O(n log n)

Algoritmul Best Fit sortează saloaneele disponibile după capacitate și alege cel mai mic salon care poate găzdui pacientul. Acest lucru minimizează spațiul neutilizat (waste minimization) și maximizează posibilitatea alocării viitoare a pacienților care necesită saloane mai mari.

```java
public RezultatAlocare bestFit(Internare internare) {
    List<Salon> saloaneDisponibile = salonService.getSaloaneDisponibile();
    saloaneDisponibile.sort(Comparator.comparing(Salon::getCapacitate));
    
    for (Salon salon : saloaneDisponibile) {
        if (salon.areCapacitate()) {
            return RezultatAlocare.succes(internare, salon, "Alocare Best Fit");
        }
    }
    
    return RezultatAlocare.esec(internare, "Nu exista saloane potrivite");
}
```

**Complexitate:** O(n log n) - sortare plus căutare liniară

**Avantaje:** Minimizează fragmentarea și optimizează utilizarea spațiului

**Utilizare:** Este folosit de algoritmul Priority-Based Allocation.

### Priority-Based Allocation - O(m × n log n)

Algoritmul Priority-Based sortează internările după prioritatea medicală (URGENTA, RIDICATA, NORMALA, SCAZUTA) și apoi alocă fiecare pacient folosind metoda Best Fit. Acest algoritm este recomandat pentru utilizare în centre medicale reale, deoarece asigură că pacienții cu urgențe medicale sunt procesați primii.

**Complexitate:** O(m × n log n) unde:
- m = numărul de internări nealocate
- n = numărul de saloane disponibile

**Avantaje:**
- Respectă prioritățile medicale
- Minimizează timpul de așteptare pentru cazuri urgente
- Optimizează utilizarea spațiului prin Best Fit

### Greedy Max Ocupare - O(m × n)

Algoritmul Greedy maximizează gradul de ocupare a saloaneor prin procesarea internărilor în ordine și alocarea folosind First Fit. Scopul este eficiența economică maximă prin umplerea rapidă a saloaneor disponibile.

**Complexitate:** O(m × n) unde:
- m = numărul de internări nealocate
- n = numărul de saloane disponibile

**Avantaje:**
- Maximizează veniturile centrului
- Reduce numărul de saloane parțial ocupate
- Execuție rapidă

**Utilizare:** Recomandat pentru optimizare financiară în perioade cu cerere mare.

### Comparație Algoritmi

| Algoritm | Complexitate | Optimizează | Recomandat pentru |
|----------|--------------|-------------|-------------------|
| First Fit | O(n) | Viteză | Helper method |
| Best Fit | O(n log n) | Spațiu | Helper method |
| Priority-Based | O(m × n log n) | Prioritate medicală | Centre medicale |
| Greedy | O(m × n) | Eficiență economică | Optimizare financiară |

## Instalare și Configurare

### Cerințe Sistem

Aplicația necesită următoarele componente instalate:
- Java JDK 17 sau superior
- Maven 3.8 sau superior
- MySQL 8.0 sau superior (sau XAMPP cu MySQL)
- IntelliJ IDEA (recomandat) sau alt IDE Java

### Pasul 1: Clonare Repository

```bash
git clone https://github.com/lavinia06/CentruRecuperare.git
cd CentruRecuperare
```

### Pasul 2: Configurare Bază de Date

Pornește serverul MySQL și creează baza de date:

```sql
CREATE DATABASE centru_recuperare 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_general_ci;
```

### Pasul 3: Configurare Aplicație

Editează fișierul `src/main/resources/application.properties` și ajustează credențialele pentru MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/centru_recuperare
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

Modifică `username` și `password` conform configurației tale MySQL.

### Pasul 4: Instalare Dependențe

```bash
mvn clean install
```

Acest command va descărca toate dependențele necesare și va compila proiectul.

### Pasul 5: Încărcare Date de Test

Pentru a testa funcționalitatea completă a aplicației, se recomandă încărcarea datelor de test care includ:
- 60 de pacienți
- 40 de saloane distribuite pe 4 etaje
- 12 terapeuți
- 8 tipuri de terapie
- 50 de internări (20 în așteptare, 20 active, 10 finalizate)

Rulează scriptul SQL din `src/main/resources/test-data.sql`:

```bash
mysql -u root -p centru_recuperare < src/main/resources/test-data.sql
```

Sau importă scriptul prin phpMyAdmin sau MySQL Workbench.

## Rulare Aplicație

### Metodă 1: IntelliJ IDEA

1. Deschide proiectul în IntelliJ IDEA
2. Așteaptă ca Maven să descarce dependențele
3. Găsește clasa `CentruRecuperareApplication.java`
4. Click dreapta pe clasă și selectează "Run"
5. Sau folosește combinația de taste Shift + F10

### Metodă 2: Maven Command Line

```bash
mvn spring-boot:run
```

### Metodă 3: Executabil JAR

```bash
mvn clean package
java -jar target/CentruRecuperare-0.0.1-SNAPSHOT.jar
```

### Acces Aplicație

După pornirea cu succes, aplicația va fi disponibilă la:

**URL:** http://localhost:8080

**Credențiale login:**
- Username: admin
- Password: admin123

Consola va afișa mesajul de confirmare: