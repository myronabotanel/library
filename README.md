# Library Management System

## 📋 Descriere
Sistem de Management al Bibliotecii este o aplicație Java modernă care oferă o soluție completă pentru gestionarea unei biblioteci. Aplicația permite gestionarea cărților, utilizatorilor, împrumuturilor și oferă funcționalități avansate pentru diferite tipuri de utilizatori (administratori, angajați și clienți).

## 🚀 Funcționalități

### Pentru Administratori
- Gestionarea completă a utilizatorilor (adăugare, ștergere, modificare)
- Gestionarea angajaților
- Vizualizarea rapoartelor și statisticilor
- Gestionarea stocului de cărți
- Monitorizarea activității sistemului

### Pentru Angajați
- Gestionarea împrumuturilor
- Procesarea returnărilor
- Căutarea și rezervarea cărților
- Asistență clienți

### Pentru Clienți
- Căutarea cărților
- Rezervarea cărților
- Vizualizarea istoricului personal
- Gestionarea contului personal

## 🛠 Tehnologii Utilizate

### Limbaje și Framework-uri
- Java 21
- JavaFX 21.0.1 (pentru interfața grafică)
- Gradle (sistem de build)

### Baza de Date
- MySQL 8.0
- JDBC pentru conectare

### Biblioteci și Dependențe
- iText7 (pentru generarea rapoartelor PDF)
- Logback (pentru logging)
- JUnit 5 (pentru testare)
- Testcontainers (pentru teste de integrare)

## 🔒 Securitate

### Autentificare și Autorizare
- Sistem de login/register securizat
- Hash-uri SHA-256 pentru parole
- Validare robustă a credențialelor
- Sistem de roluri (ADMINISTRATOR, EMPLOYEE, CUSTOMER)

### Validări
- Validare email pentru username
- Parole complexe (minim 8 caractere, caractere speciale, cifre)
- Prevenire SQL injection prin PreparedStatements
- Validare input la toate nivelurile

### Protecție Date
- Parole hash-uite în baza de date
- Conexiuni securizate la baza de date
- Gestionare sesiuni utilizator
- Logging pentru audit și debugging

## 🏗 Arhitectură

### Pattern-uri de Design
- Factory Pattern (pentru crearea componentelor)
- Builder Pattern (pentru construirea obiectelor)
- Repository Pattern (pentru accesul la date)
- Service Pattern (pentru logica de business)
- MVC (Model-View-Controller)

### Structura Proiectului
```
src/main/java/org/example/
├── controller/     # Controlere pentru UI
├── model/         # Entități și validatori
├── repository/    # Acces la date
├── service/       # Logică de business
├── view/          # Interfețe UI
└── launcher/      # Puncte de intrare
```

## 📦 Instalare și Configurare

### Cerințe Preliminare
- Java 21 sau mai nou
- MySQL 8.0 sau mai nou
- Gradle 8.1 sau mai nou

### Pași de Instalare
1. Clonează repository-ul
```bash
git clone [repository-url]
```

2. Configurează baza de date
- Creează o bază de date MySQL
- Actualizează credențialele în fișierul de configurare

3. Build și Rulare
```bash
./gradlew build
./gradlew run
```

## 🧪 Testare

### Tipuri de Teste
- Teste unitare (JUnit 5)
- Teste de integrare (Testcontainers)
- Teste de performanță

### Rulare Teste
```bash
./gradlew test
```

## 📊 Performanță

### Optimizări
- Caching implementat pentru cărți
- Queries optimizate
- Conexiuni la baza de date gestionate eficient

### Monitorizare
- Logging pentru debugging
- Metrici de performanță
- Rapoarte de utilizare

## 🔄 Dezvoltare

### Contribuție
1. Fork repository-ul
2. Creează un branch nou
3. Commit schimbările
4. Push la branch
5. Creează un Pull Request

### Standarde de Cod
- Respectă convențiile de nume Java
- Documentează codul complex
- Scrie teste pentru funcționalități noi

## 📝 Licență
[Specificați licența proiectului]

## 👥 Autori
[Specificați autorii proiectului]

## 🙏 Mulțumiri
[Opcional: Mulțumiri speciale sau recunoașteri]