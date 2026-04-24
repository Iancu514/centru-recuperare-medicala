-- ========================================
-- SCRIPT GENERARE DATE DE TEST
-- Centru Recuperare Medicala
-- 40 Saloane pe 4 Etaje, 60 Pacienti
-- ========================================

-- Stergem datele existente
DELETE FROM internari;
DELETE FROM pacienti;
DELETE FROM salon;
DELETE FROM terapeutii;
DELETE FROM tipuri_terapie;

-- ========================================
-- SALOANE (40 saloane pe 4 etaje)
-- Coloane: numar, etaj, tip, capacitate, disponibil, observatii
-- ========================================

-- ETAJ 1 - Saloane 101-110
INSERT INTO salon (numar, etaj, capacitate, disponibil, observatii) VALUES
                                                                        ('101', 1, 1, true, 'Salon individual - cazuri urgente'),
                                                                        ('102', 1, 1, true, 'Salon individual - monitorizare intensiva'),
                                                                        ('103', 1, 2, true, 'Salon dublu - recuperare rapida'),
                                                                        ('104', 1, 2, true, 'Salon dublu - post-operator'),
                                                                        ('105', 1, 1, true, 'Salon individual VIP'),
                                                                        ('106', 1, 2, true, 'Salon dublu - terapie intensiva'),
                                                                        ('107', 1, 3, true, 'Salon triplu - observatie'),
                                                                        ('108', 1, 2, true, 'Salon dublu - recuperare'),
                                                                        ('109', 1, 1, true, 'Salon individual - izolare'),
                                                                        ('110', 1, 3, true, 'Salon triplu - standard');

-- ETAJ 2 - Saloane 201-210
INSERT INTO salon (numar, etaj, capacitate, disponibil, observatii) VALUES
                                                                        ('201', 2, 2, true, 'Salon dublu - recuperare articulara'),
                                                                        ('202', 2, 3, true, 'Salon triplu - kinetoterapie'),
                                                                        ('203', 2, 2, true, 'Salon dublu - terapie fizica'),
                                                                        ('204', 2, 4, true, 'Salon patru paturi - standard'),
                                                                        ('205', 2, 2, true, 'Salon dublu - recuperare lombara'),
                                                                        ('206', 2, 3, true, 'Salon triplu - terapie complexa'),
                                                                        ('207', 2, 2, true, 'Salon dublu - hidroterapie'),
                                                                        ('208', 2, 4, true, 'Salon patru paturi - grup'),
                                                                        ('209', 2, 2, true, 'Salon dublu - electroterapie'),
                                                                        ('210', 2, 3, true, 'Salon triplu - recuperare');

-- ETAJ 3 - Saloane 301-310
INSERT INTO salon (numar, etaj, capacitate, disponibil, observatii) VALUES
                                                                        ('301', 3, 4, true, 'Salon patru paturi - termen lung'),
                                                                        ('302', 3, 3, true, 'Salon triplu - geriatrie'),
                                                                        ('303', 3, 4, true, 'Salon patru paturi - recuperare'),
                                                                        ('304', 3, 2, true, 'Salon dublu - pensionari'),
                                                                        ('305', 3, 5, true, 'Salon cinci paturi - economica'),
                                                                        ('306', 3, 4, true, 'Salon patru paturi - standard'),
                                                                        ('307', 3, 3, true, 'Salon triplu - terapie usoara'),
                                                                        ('308', 3, 4, true, 'Salon patru paturi - grup'),
                                                                        ('309', 3, 2, true, 'Salon dublu - confort'),
                                                                        ('310', 3, 5, true, 'Salon cinci paturi - mare');

-- ETAJ 4 - Saloane 401-410
INSERT INTO salon (numar, etaj, capacitate, disponibil, observatii) VALUES
                                                                        ('401', 4, 2, true, 'Salon dublu - neurologie'),
                                                                        ('402', 4, 3, true, 'Salon triplu - ortopedie'),
                                                                        ('403', 4, 2, true, 'Salon dublu - cardiologie'),
                                                                        ('404', 4, 4, true, 'Salon patru paturi - pneumologie'),
                                                                        ('405', 4, 1, true, 'Salon individual - pre-operator'),
                                                                        ('406', 4, 3, true, 'Salon triplu - pediatrie'),
                                                                        ('407', 4, 2, true, 'Salon dublu - dermatologie'),
                                                                        ('408', 4, 4, true, 'Salon patru paturi - mixte'),
                                                                        ('409', 4, 2, true, 'Salon dublu - endocrinologie'),
                                                                        ('410', 4, 3, true, 'Salon triplu - observatie');

-- ========================================
-- TIPURI TERAPIE (8 tipuri)
-- Coloane: denumire, descriere, durata_minute, activ
-- ========================================
INSERT INTO tipuri_terapie (denumire, descriere, durata_minute, activ) VALUES
                                                                           ('Kinetoterapie', 'Terapie prin miscare pentru recuperare motorie', 60, true),
                                                                           ('Electroterapie', 'Utilizare curenti electrici pentru tratament', 45, true),
                                                                           ('Hidroterapie', 'Terapie in apa pentru recuperare articulara', 90, true),
                                                                           ('Termoterapie', 'Tratament prin caldura sau frig', 30, true),
                                                                           ('Masaj terapeutic', 'Masaj specializat pentru recuperare musculara', 60, true),
                                                                           ('Magnetoterapie', 'Terapie prin camp magnetic', 40, true),
                                                                           ('Laser terapie', 'Tratament cu laser de joasa intensitate', 20, true),
                                                                           ('Ultrasunete', 'Terapie cu ultrasunete pentru recuperare', 30, true);

-- ========================================
-- TERAPEUȚI (12 terapeuți)
-- Coloane: nume, prenume, specializare, cnp, data_angajare, email, telefon, ani_experienta, observatii, activ
-- ========================================
INSERT INTO terapeutii (nume, prenume, specializare, email, telefon, ani_experienta, activ) VALUES
                                                                                               ('Popescu', 'Ion', 'FIZIOTERAPEUT', 'ion.popescu@centru.ro', '0721234567', 10, true),
                                                                                               ('Ionescu', 'Maria', 'KINETOTERAPEUT', 'maria.ionescu@centru.ro', '0722345678', 8, true),
                                                                                               ('Georgescu', 'Ana', 'MASEUR', 'ana.georgescu@centru.ro', '0723456789', 5, true),
                                                                                               ('Vasilescu', 'Mihai', 'FIZIOTERAPEUT', 'mihai.vasilescu@centru.ro', '0724567890', 12, true),
                                                                                               ('Constantinescu', 'Elena', 'KINETOTERAPEUT', 'elena.const@centru.ro', '0725678901', 7, true),
                                                                                               ('Dumitrescu', 'Andrei', 'FIZIOTERAPEUT', 'andrei.dumit@centru.ro', '0726789012', 15, true),
                                                                                               ('Stanescu', 'Ioana', 'MASEUR', 'ioana.stanescu@centru.ro', '0727890123', 6, true),
                                                                                               ('Marinescu', 'Gabriel', 'KINETOTERAPEUT', 'gabriel.marin@centru.ro', '0728901234', 9, true),
                                                                                               ('Popa', 'Cristian', 'FIZIOTERAPEUT', 'cristian.popa@centru.ro', '0729012345', 11, true),
                                                                                               ('Radu', 'Laura', 'KINETOTERAPEUT', 'laura.radu@centru.ro', '0730123456', 6, true),
                                                                                               ('Stoica', 'Florin', 'MASEUR', 'florin.stoica@centru.ro', '0731234567', 8, true),
                                                                                               ('Munteanu', 'Diana', 'FIZIOTERAPEUT', 'diana.munteanu@centru.ro', '0732345678', 13, true);

-- ========================================
-- PACIENȚI (60 pacienți)
-- Coloane: nume, prenume, cnp, data_nasterii, adresa, telefon, status, salon_id, diagnostic, data_inregistrare
-- ========================================

-- BATCH 1 (1-20)
INSERT INTO pacienti (nume, prenume, cnp, data_nasterii, adresa, telefon) VALUES
                                                                              ('Popescu', 'Ion', '1850512345678', '1985-05-12', 'Str. Libertatii, nr. 10, Timisoara', '0731234567'),
                                                                              ('Ionescu', 'Maria', '2920815234567', '1992-08-15', 'Str. Victoriei, nr. 25, Timisoara', '0732345678'),
                                                                              ('Georgescu', 'Ana', '2880320456789', '1988-03-20', 'Bd. Revolutiei, nr. 5, Timisoara', '0733456789'),
                                                                              ('Vasilescu', 'Mihai', '1750910123456', '1975-09-10', 'Str. Eroilor, nr. 15, Timisoara', '0734567890'),
                                                                              ('Constantinescu', 'Elena', '2950625345678', '1995-06-25', 'Str. Pacii, nr. 30, Timisoara', '0735678901'),
                                                                              ('Dumitrescu', 'Andrei', '1820418234567', '1982-04-18', 'Bd. Cetatii, nr. 8, Timisoara', '0736789012'),
                                                                              ('Stanescu', 'Ioana', '2970730456789', '1997-07-30', 'Str. Florilor, nr. 12, Timisoara', '0737890123'),
                                                                              ('Marinescu', 'Gabriel', '1880922123456', '1988-09-22', 'Str. Unirii, nr. 20, Timisoara', '0738901234'),
                                                                              ('Popa', 'Cristina', '2930514345678', '1993-05-14', 'Bd. Dacia, nr. 7, Timisoara', '0739012345'),
                                                                              ('Munteanu', 'Alexandru', '1770808234567', '1977-08-08', 'Str. Mihai Viteazu, nr. 18, Timisoara', '0740123456'),
                                                                              ('Radu', 'Simona', '2860220456789', '1986-02-20', 'Str. Stefan cel Mare, nr. 22, Timisoara', '0741234567'),
                                                                              ('Stoica', 'Dan', '1910612123456', '1991-06-12', 'Bd. Traian, nr. 14, Timisoara', '0742345678'),
                                                                              ('Niță', 'Andreea', '2940925345678', '1994-09-25', 'Str. Avram Iancu, nr. 9, Timisoara', '0743456789'),
                                                                              ('Diaconu', 'Florin', '1830305234567', '1983-03-05', 'Str. Horea, nr. 16, Timisoara', '0744567890'),
                                                                              ('Preda', 'Monica', '2891118456789', '1989-11-18', 'Bd. Vasile Parvan, nr. 11, Timisoara', '0745678901'),
                                                                              ('Tudorache', 'Vlad', '1960428123456', '1996-04-28', 'Str. Closca, nr. 13, Timisoara', '0746789012'),
                                                                              ('Matei', 'Diana', '2870715345678', '1987-07-15', 'Str. Crisan, nr. 19, Timisoara', '0747890123'),
                                                                              ('Barbu', 'Cosmin', '1790202234567', '1979-02-02', 'Bd. Eroilor, nr. 6, Timisoara', '0748901234'),
                                                                              ('Ilie', 'Gabriela', '2921010456789', '1992-10-10', 'Str. Libertatii, nr. 24, Timisoara', '0749012345'),
                                                                              ('Stefan', 'Razvan', '1840623123456', '1984-06-23', 'Str. Independentei, nr. 17, Timisoara', '0750123456');

-- BATCH 2 (21-40)
INSERT INTO pacienti (nume, prenume, cnp, data_nasterii, adresa, telefon) VALUES
                                                                              ('Lungu', 'Carmen', '2950828345678', '1995-08-28', 'Bd. Regina Maria, nr. 21, Timisoara', '0751234567'),
                                                                              ('Toma', 'Sorin', '1760914234567', '1976-09-14', 'Str. 1 Decembrie, nr. 3, Timisoara', '0752345678'),
                                                                              ('Moldovan', 'Raluca', '2881127456789', '1988-11-27', 'Str. Mihai Eminescu, nr. 28, Timisoara', '0753456789'),
                                                                              ('Balan', 'Ionut', '1930402123456', '1993-04-02', 'Bd. Take Ionescu, nr. 12, Timisoara', '0754567890'),
                                                                              ('Nistor', 'Laura', '2970615345678', '1997-06-15', 'Str. Nicolae Balcescu, nr. 26, Timisoara', '0755678901'),
                                                                              ('Lazar', 'Adrian', '1810820234567', '1981-08-20', 'Str. Vasile Alecsandri, nr. 4, Timisoara', '0756789012'),
                                                                              ('Ciobanu', 'Alina', '2941105456789', '1994-11-05', 'Bd. Mihai Viteazu, nr. 23, Timisoara', '0757890123'),
                                                                              ('Petre', 'Bogdan', '1870318123456', '1987-03-18', 'Str. Tudor Vladimirescu, nr. 15, Timisoara', '0758901234'),
                                                                              ('Cojocaru', 'Oana', '2960722345678', '1996-07-22', 'Str. Decebal, nr. 27, Timisoara', '0759012345'),
                                                                              ('Florea', 'Marius', '1780904234567', '1978-09-04', 'Bd. Carol I, nr. 9, Timisoara', '0760123456'),
                                                                              ('Ene', 'Daniela', '2920503456789', '1992-05-03', 'Str. Ferdinand, nr. 31, Timisoara', '0761234567'),
                                                                              ('Grigorescu', 'Victor', '1850716123456', '1985-07-16', 'Bd. Iuliu Maniu, nr. 18, Timisoara', '0762345678'),
                                                                              ('Sandu', 'Nicoleta', '2961028345678', '1996-10-28', 'Str. George Cosbuc, nr. 14, Timisoara', '0763456789'),
                                                                              ('Vlaicu', 'Sorin', '1730209234567', '1973-02-09', 'Str. Ion Creanga, nr. 22, Timisoara', '0764567890'),
                                                                              ('Oproiu', 'Adriana', '2880421456789', '1988-04-21', 'Bd. Petre Roman, nr. 6, Timisoara', '0765678901'),
                                                                              ('Alexandrescu', 'Radu', '1940612123456', '1994-06-12', 'Str. Corneliu Coposu, nr. 29, Timisoara', '0766789012'),
                                                                              ('Ungureanu', 'Mihaela', '2970825345678', '1997-08-25', 'Str. Octavian Goga, nr. 11, Timisoara', '0767890123'),
                                                                              ('Marin', 'Catalin', '1810914234567', '1981-09-14', 'Bd. George Enescu, nr. 17, Timisoara', '0768901234'),
                                                                              ('Stroe', 'Anca', '2931107456789', '1993-11-07', 'Str. Constantin Brancusi, nr. 8, Timisoara', '0769012345'),
                                                                              ('Badea', 'Cristian', '1870220123456', '1987-02-20', 'Str. Lucian Blaga, nr. 25, Timisoara', '0770123456');

-- BATCH 3 (41-60)
INSERT INTO pacienti (nume, prenume, cnp, data_nasterii, adresa, telefon) VALUES
                                                                              ('Dobre', 'Lavinia', '2950503345678', '1995-05-03', 'Bd. Magheru, nr. 13, Timisoara', '0771234567'),
                                                                              ('Iancu', 'George', '1820716234567', '1982-07-16', 'Str. Alexandru Ioan Cuza, nr. 19, Timisoara', '0772345678'),
                                                                              ('Cristea', 'Roxana', '2961028456789', '1996-10-28', 'Str. Ion Luca Caragiale, nr. 7, Timisoara', '0773456789'),
                                                                              ('Manea', 'Liviu', '1740209123456', '1974-02-09', 'Bd. Romana, nr. 21, Timisoara', '0774567890'),
                                                                              ('Anghel', 'Ramona', '2890421345678', '1989-04-21', 'Str. Dimitrie Cantemir, nr. 16, Timisoara', '0775678901'),
                                                                              ('Dumitru', 'Stefan', '1950612234567', '1995-06-12', 'Str. Mihail Sadoveanu, nr. 24, Timisoara', '0776789012'),
                                                                              ('Enache', 'Paula', '2980825456789', '1998-08-25', 'Bd. Ion Mihalache, nr. 10, Timisoara', '0777890123'),
                                                                              ('Voicu', 'Nicolae', '1800914123456', '1980-09-14', 'Str. Vasile Conta, nr. 15, Timisoara', '0778901234'),
                                                                              ('Drăghici', 'Simona', '2941107345678', '1994-11-07', 'Str. Barbu Delavrancea, nr. 28, Timisoara', '0779012345'),
                                                                              ('Nica', 'Claudiu', '1860220234567', '1986-02-20', 'Bd. Carol Davila, nr. 12, Timisoara', '0780123456'),
                                                                              ('Teodorovici', 'Valentina', '2960503456789', '1996-05-03', 'Str. Mihai Sadoveanu, nr. 20, Timisoara', '0781234567'),
                                                                              ('Mircea', 'Constantin', '1830716123456', '1983-07-16', 'Bd. Alexandru Lapusneanu, nr. 9, Timisoara', '0782345678'),
                                                                              ('Pascale', 'Ioana', '2971028345678', '1997-10-28', 'Str. Aurel Vlaicu, nr. 14, Timisoara', '0783456789'),
                                                                              ('Serban', 'Marcel', '1750209234567', '1975-02-09', 'Str. Traian Vuia, nr. 26, Timisoara', '0784567890'),
                                                                              ('Carp', 'Bianca', '2900421456789', '1990-04-21', 'Bd. Henri Coanda, nr. 11, Timisoara', '0785678901'),
                                                                              ('Zamfir', 'Valentin', '1960612123456', '1996-06-12', 'Str. Spiru Haret, nr. 23, Timisoara', '0786789012'),
                                                                              ('Apostol', 'Gabriela', '2990825345678', '1999-08-25', 'Str. Liviu Rebreanu, nr. 8, Timisoara', '0787890123'),
                                                                              ('Pintilie', 'Dorin', '1790914234567', '1979-09-14', 'Bd. Ion Campineanu, nr. 17, Timisoara', '0788901234'),
                                                                              ('Tomescu', 'Adriana', '2951107456789', '1995-11-07', 'Str. George Bacovia, nr. 30, Timisoara', '0789012345'),
                                                                              ('Rusu', 'Eugen', '1850220123456', '1985-02-20', 'Str. Tudor Arghezi, nr. 13, Timisoara', '0790123456');

-- ========================================
-- INTERNARI (50 internari)
-- ========================================

-- Internari IN ASTEPTARE (20) - pentru testare algoritmi
-- Folosim ID-urile reale ale pacientilor (presupunem ca încep de la 1)
INSERT INTO internari (pacient_id, data_internare, durata_estimata, prioritate, status, observatii)
SELECT id, '2026-04-20', 7, 'URGENTA', 'IN_ASTEPTARE', 'Recuperare post-operatorie genunchi' FROM pacienti WHERE cnp = '1850512345678'
UNION ALL
SELECT id, '2026-04-21', 5, 'RIDICATA', 'IN_ASTEPTARE', 'Terapie intensiva dupa fractura' FROM pacienti WHERE cnp = '2920815234567'
UNION ALL
SELECT id, '2026-04-22', 10, 'NORMALA', 'IN_ASTEPTARE', 'Recuperare lombara' FROM pacienti WHERE cnp = '2880320456789'
UNION ALL
SELECT id, '2026-04-22', 3, 'URGENTA', 'IN_ASTEPTARE', 'Recuperare urgenta post-accident' FROM pacienti WHERE cnp = '1750910123456'
UNION ALL
SELECT id, '2026-04-23', 14, 'NORMALA', 'IN_ASTEPTARE', 'Recuperare articulara' FROM pacienti WHERE cnp = '2950625345678'
UNION ALL
SELECT id, '2026-04-23', 6, 'RIDICATA', 'IN_ASTEPTARE', 'Terapie umar' FROM pacienti WHERE cnp = '1820418234567'
UNION ALL
SELECT id, '2026-04-24', 8, 'SCAZUTA', 'IN_ASTEPTARE', 'Kinetoterapie preventiva' FROM pacienti WHERE cnp = '2970730456789'
UNION ALL
SELECT id, '2026-04-24', 4, 'NORMALA', 'IN_ASTEPTARE', 'Recuperare sold' FROM pacienti WHERE cnp = '1880922123456'
UNION ALL
SELECT id, '2026-04-25', 12, 'RIDICATA', 'IN_ASTEPTARE', 'Terapie complexa coloana' FROM pacienti WHERE cnp = '2930514345678'
UNION ALL
SELECT id, '2026-04-25', 5, 'NORMALA', 'IN_ASTEPTARE', 'Recuperare glezna' FROM pacienti WHERE cnp = '1770808234567'
UNION ALL
SELECT id, '2026-04-26', 9, 'URGENTA', 'IN_ASTEPTARE', 'Caz urgent politraumatism' FROM pacienti WHERE cnp = '2860220456789'
UNION ALL
SELECT id, '2026-04-26', 6, 'RIDICATA', 'IN_ASTEPTARE', 'Recuperare cervicala' FROM pacienti WHERE cnp = '1910612123456'
UNION ALL
SELECT id, '2026-04-27', 11, 'NORMALA', 'IN_ASTEPTARE', 'Terapie lombara' FROM pacienti WHERE cnp = '2940925345678'
UNION ALL
SELECT id, '2026-04-27', 4, 'SCAZUTA', 'IN_ASTEPTARE', 'Preventie' FROM pacienti WHERE cnp = '1830305234567'
UNION ALL
SELECT id, '2026-04-28', 7, 'RIDICATA', 'IN_ASTEPTARE', 'Post-fractura gambier' FROM pacienti WHERE cnp = '2891118456789'
UNION ALL
SELECT id, '2026-04-28', 15, 'NORMALA', 'IN_ASTEPTARE', 'Recuperare degenerativa' FROM pacienti WHERE cnp = '1960428123456'
UNION ALL
SELECT id, '2026-04-29', 5, 'URGENTA', 'IN_ASTEPTARE', 'Urgenta neurologica' FROM pacienti WHERE cnp = '2870715345678'
UNION ALL
SELECT id, '2026-04-29', 8, 'RIDICATA', 'IN_ASTEPTARE', 'Terapie intensiva' FROM pacienti WHERE cnp = '1790202234567'
UNION ALL
SELECT id, '2026-04-30', 10, 'NORMALA', 'IN_ASTEPTARE', 'Recuperare standard' FROM pacienti WHERE cnp = '2921010456789'
UNION ALL
SELECT id, '2026-04-30', 6, 'SCAZUTA', 'IN_ASTEPTARE', 'Mentenanta' FROM pacienti WHERE cnp = '1840623123456';

-- Internari ACTIVE (20) - cu saloane alocate
INSERT INTO internari (pacient_id, salon_id, data_internare, durata_estimata, prioritate, status, observatii)
SELECT p.id, s.id, '2026-04-15', 7, 'URGENTA', 'ACTIVA', 'Salon 101 - Etaj 1'
FROM pacienti p, salon s WHERE p.cnp = '2950828345678' AND s.numar = '101'
UNION ALL
SELECT p.id, s.id, '2026-04-15', 5, 'URGENTA', 'ACTIVA', 'Salon 102 - Etaj 1'
FROM pacienti p, salon s WHERE p.cnp = '1760914234567' AND s.numar = '102'
UNION ALL
SELECT p.id, s.id, '2026-04-16', 10, 'RIDICATA', 'ACTIVA', 'Salon 103 - Etaj 1'
FROM pacienti p, salon s WHERE p.cnp = '2881127456789' AND s.numar = '103'
UNION ALL
SELECT p.id, s.id, '2026-04-16', 6, 'RIDICATA', 'ACTIVA', 'Salon 104 - Etaj 1'
FROM pacienti p, salon s WHERE p.cnp = '1930402123456' AND s.numar = '104'
UNION ALL
SELECT p.id, s.id, '2026-04-17', 3, 'URGENTA', 'ACTIVA', 'Salon 105 VIP - Etaj 1'
FROM pacienti p, salon s WHERE p.cnp = '2970615345678' AND s.numar = '105'
UNION ALL
SELECT p.id, s.id, '2026-04-17', 14, 'NORMALA', 'ACTIVA', 'Salon 201 - Etaj 2'
FROM pacienti p, salon s WHERE p.cnp = '1810820234567' AND s.numar = '201'
UNION ALL
SELECT p.id, s.id, '2026-04-18', 8, 'RIDICATA', 'ACTIVA', 'Salon 202 - Etaj 2'
FROM pacienti p, salon s WHERE p.cnp = '2941105456789' AND s.numar = '202'
UNION ALL
SELECT p.id, s.id, '2026-04-18', 5, 'NORMALA', 'ACTIVA', 'Salon 203 - Etaj 2'
FROM pacienti p, salon s WHERE p.cnp = '1870318123456' AND s.numar = '203'
UNION ALL
SELECT p.id, s.id, '2026-04-19', 12, 'RIDICATA', 'ACTIVA', 'Salon 204 - Etaj 2'
FROM pacienti p, salon s WHERE p.cnp = '2960722345678' AND s.numar = '204'
UNION ALL
SELECT p.id, s.id, '2026-04-19', 7, 'NORMALA', 'ACTIVA', 'Salon 205 - Etaj 2'
FROM pacienti p, salon s WHERE p.cnp = '1780904234567' AND s.numar = '205'
UNION ALL
SELECT p.id, s.id, '2026-04-20', 10, 'NORMALA', 'ACTIVA', 'Salon 301 - Etaj 3'
FROM pacienti p, salon s WHERE p.cnp = '2920503456789' AND s.numar = '301'
UNION ALL
SELECT p.id, s.id, '2026-04-20', 9, 'RIDICATA', 'ACTIVA', 'Salon 302 - Etaj 3'
FROM pacienti p, salon s WHERE p.cnp = '1850716123456' AND s.numar = '302'
UNION ALL
SELECT p.id, s.id, '2026-04-21', 15, 'NORMALA', 'ACTIVA', 'Salon 303 - Etaj 3'
FROM pacienti p, salon s WHERE p.cnp = '2961028345678' AND s.numar = '303'
UNION ALL
SELECT p.id, s.id, '2026-04-21', 6, 'SCAZUTA', 'ACTIVA', 'Salon 304 - Etaj 3'
FROM pacienti p, salon s WHERE p.cnp = '1730209234567' AND s.numar = '304'
UNION ALL
SELECT p.id, s.id, '2026-04-22', 20, 'NORMALA', 'ACTIVA', 'Salon 305 - Etaj 3 - Termen lung'
FROM pacienti p, salon s WHERE p.cnp = '2880421456789' AND s.numar = '305'
UNION ALL
SELECT p.id, s.id, '2026-04-22', 4, 'RIDICATA', 'ACTIVA', 'Salon 401 - Etaj 4'
FROM pacienti p, salon s WHERE p.cnp = '1940612123456' AND s.numar = '401'
UNION ALL
SELECT p.id, s.id, '2026-04-23', 8, 'NORMALA', 'ACTIVA', 'Salon 402 - Etaj 4'
FROM pacienti p, salon s WHERE p.cnp = '2970825345678' AND s.numar = '402'
UNION ALL
SELECT p.id, s.id, '2026-04-23', 5, 'URGENTA', 'ACTIVA', 'Salon 403 - Etaj 4'
FROM pacienti p, salon s WHERE p.cnp = '1810914234567' AND s.numar = '403'
UNION ALL
SELECT p.id, s.id, '2026-04-24', 11, 'RIDICATA', 'ACTIVA', 'Salon 404 - Etaj 4'
FROM pacienti p, salon s WHERE p.cnp = '2931107456789' AND s.numar = '404'
UNION ALL
SELECT p.id, s.id, '2026-04-24', 3, 'URGENTA', 'ACTIVA', 'Salon 405 - Etaj 4'
FROM pacienti p, salon s WHERE p.cnp = '1870220123456' AND s.numar = '405';

-- Internari FINALIZATE (10)
INSERT INTO internari (pacient_id, salon_id, data_internare, data_externare, durata_estimata, prioritate, status, observatii)
SELECT p.id, s.id, '2026-04-01', '2026-04-08', 7, 'NORMALA', 'FINALIZATA', 'Recuperare completa - Salon 106'
FROM pacienti p, salon s WHERE p.cnp = '2950503345678' AND s.numar = '106'
UNION ALL
SELECT p.id, s.id, '2026-04-03', '2026-04-10', 7, 'RIDICATA', 'FINALIZATA', 'Rezultate excelente - Salon 107'
FROM pacienti p, salon s WHERE p.cnp = '1820716234567' AND s.numar = '107'
UNION ALL
SELECT p.id, s.id, '2026-04-05', '2026-04-09', 4, 'NORMALA', 'FINALIZATA', 'Finalizat cu succes - Salon 108'
FROM pacienti p, salon s WHERE p.cnp = '2961028456789' AND s.numar = '108'
UNION ALL
SELECT p.id, s.id, '2026-04-06', '2026-04-12', 6, 'RIDICATA', 'FINALIZATA', 'Evolutie favorabila - Salon 206'
FROM pacienti p, salon s WHERE p.cnp = '1740209123456' AND s.numar = '206'
UNION ALL
SELECT p.id, s.id, '2026-04-08', '2026-04-13', 5, 'NORMALA', 'FINALIZATA', 'Externare la timp - Salon 207'
FROM pacienti p, salon s WHERE p.cnp = '2890421345678' AND s.numar = '207'
UNION ALL
SELECT p.id, s.id, '2026-04-10', '2026-04-17', 7, 'RIDICATA', 'FINALIZATA', 'Recuperare completa - Salon 306'
FROM pacienti p, salon s WHERE p.cnp = '1950612234567' AND s.numar = '306'
UNION ALL
SELECT p.id, s.id, '2026-04-11', '2026-04-15', 4, 'URGENTA', 'FINALIZATA', 'Caz urgent rezolvat - Salon 307'
FROM pacienti p, salon s WHERE p.cnp = '2980825456789' AND s.numar = '307'
UNION ALL
SELECT p.id, s.id, '2026-04-12', '2026-04-19', 7, 'NORMALA', 'FINALIZATA', 'Standard finalizat - Salon 406'
FROM pacienti p, salon s WHERE p.cnp = '1800914123456' AND s.numar = '406'
UNION ALL
SELECT p.id, s.id, '2026-04-13', '2026-04-18', 5, 'RIDICATA', 'FINALIZATA', 'Rezultate bune - Salon 407'
FROM pacienti p, salon s WHERE p.cnp = '2941107345678' AND s.numar = '407'
UNION ALL
SELECT p.id, s.id, '2026-04-14', '2026-04-20', 6, 'NORMALA', 'FINALIZATA', 'Finalizat conform plan - Salon 408'
FROM pacienti p, salon s WHERE p.cnp = '1860220234567' AND s.numar = '408';