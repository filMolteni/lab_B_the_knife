TheKnife – Piattaforma di gestione ristoranti, recensioni e preferiti
Descrizione generale
TheKnife è un sistema completo per la gestione centralizzata di ristoranti provenienti da fonti differenti, recensioni degli utenti, risposte dei gestori e liste personalizzate di preferiti.
L’applicazione integra funzionalità per utenti clienti e gestori, offrendo un flusso operativo completo dalla consultazione dei ristoranti alla gestione delle interazioni.

Funzionalità principali
Autenticazione utenti con ruoli differenziati

Ricerca e consultazione ristoranti

Gestione recensioni (creazione, modifica, eliminazione)

Gestione risposte alle recensioni da parte dei gestori

Sistema di preferiti con associazione utente–ristorante

Creazione e gestione ristoranti da parte dei gestori

Integrazione dataset esterni per ristoranti predefiniti

Pulsante “Copia” per operazioni rapide

Componenti principali
Modulo autenticazione

Modulo gestione ristoranti

Modulo recensioni e risposte

Modulo preferiti

Modulo ricerca

Interfaccia grafica (JavaFX)

DatabasePopulator

DAO (Data Access Object)

Database e popolamento
Il progetto include una classe dedicata al popolamento del database (DatabasePopulator), responsabile di:

creazione automatica delle tabelle

inserimento dei dati iniziali

caricamento dei dataset esterni

sincronizzazione dei ristoranti predefiniti

Il popolamento può essere eseguito tramite DatabasePopulator.jar.

Stato del progetto
Il sistema è stato testato dopo l’aggiornamento delle dipendenze, del popolamento e della struttura delle cartelle.
L’applicazione risulta stabile, funzionante e pronta all’uso.

Avvio del progetto
Requisiti
Java 21+

MySQL

Librerie esterne già incluse nella cartella lib/

JavaFX SDK già incluso in lib/javafx-sdk-21.0.2/

File JAR eseguibili nella cartella principale del progetto

Nessuna modifica ai percorsi è necessaria.

1️⃣ Avvio del DatabasePopulator
Per creare automaticamente tabelle, dati iniziali e importare i dataset:

Codice
java -jar DatabasePopulator.jar

2️⃣ Avvio del server
Codice
java -jar server.jar
Il server:

si connette al database

gestisce le richieste del client

fornisce API interne per autenticazione, ristoranti, recensioni, preferiti

3️⃣ Avvio del client (JavaFX)
Il client utilizza JavaFX, già incluso nel progetto.

Codice
java --module-path "lib/javafx-sdk-21.0.2/lib" --add-modules javafx.controls,javafx.fxml -jar client.jar
Il percorso è relativo, quindi portabile su qualsiasi macchina.

4️⃣ Avvio tramite file .bat (opzionale)
run-client.bat
Codice
java --module-path "lib/javafx-sdk-21.0.2/lib" --add-modules javafx.controls,javafx.fxml -jar client.jar
run-server.bat
Codice
java -jar server.jar
run-populator.bat
Codice
java -jar DatabasePopulator.jar
Esportazione JAR (VS Code)
Il progetto è configurato per essere esportato tramite:

Java Projects → Export Jar

oppure CTRL + SHIFT + P → “Export Jar”

VS Code genera automaticamente:

manifest

classpath

JAR eseguibili

Note finali
Il progetto è stato sviluppato con attenzione a:

modularità

separazione delle responsabilità

manutenibilità

La struttura attuale consente estensioni future senza modifiche invasive.
