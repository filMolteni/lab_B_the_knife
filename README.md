NOME DEL PROGETTO
TheKnife – Piattaforma di gestione ristoranti, recensioni e preferiti

DESCRIZIONE GENERALE
TheKnife è un sistema progettato per la gestione centralizzata di ristoranti provenienti da fonti differenti, recensioni degli utenti, risposte dei gestori e liste personalizzate di preferiti.
L’applicazione integra funzionalità per utenti clienti e utenti gestori, garantendo un flusso operativo completo dalla consultazione dei ristoranti alla gestione delle interazioni.

FUNZIONALITA PRINCIPALI

Autenticazione utenti con ruoli differenziati

Ricerca e consultazione ristoranti

Gestione recensioni (creazione, modifica, eliminazione)

Gestione risposte alle recensioni da parte dei gestori

Sistema di preferiti con associazione utente–ristorante

Creazione e gestione ristoranti da parte dei gestori

Integrazione dataset esterni per ristoranti predefiniti

Pulsante “Copia” per operazioni rapide su contenuti generati

COMPONENTI PRINCIPALI

Modulo autenticazione

Modulo gestione ristoranti

Modulo recensioni e risposte

Modulo preferiti

Modulo ricerca

Modulo interfaccia grafica

Modulo di popolamento dati (DatabasePopulator)

Modulo di accesso al database (DAO)

DATABASE E POPOLAMENTO
Il progetto utilizza una classe dedicata al popolamento del database, responsabile dell’inserimento dei dati iniziali e della sincronizzazione con i dataset esterni.
I percorsi delle librerie, dei dataset e dei file di supporto sono stati aggiornati e normalizzati per garantire compatibilità e caricamento corretto in fase di esecuzione.
Il populator esegue automaticamente la creazione delle entità e l’inserimento dei dati di base necessari al funzionamento dell’applicazione.

NOTA IMPORTANTE SUI PERCORSI  
All’interno del progetto sono presenti file che richiedono la modifica manuale dei percorsi locali in base alla macchina su cui viene eseguito il sistema.
In particolare:

nel file settings.json vanno aggiornati i percorsi delle librerie esterne

nella classe DatabasePopulator vanno aggiornati i percorsi relativi ai dataset e ai file SQL

Questi percorsi devono essere adattati dall’utente in base alla propria struttura locale.

GESTIONE FILE E LIBRERIE
Tutti i path relativi a dataset, driver, risorse e file di configurazione sono stati rivisti e aggiornati.
La struttura del progetto è ora coerente e permette il caricamento corretto delle dipendenze senza errori di percorso o riferimenti mancanti.

INTERFACCIA E USABILITA
È stato introdotto un pulsante “Copia” nelle sezioni che richiedono operazioni rapide, come la copia di identificativi, query, testi o contenuti generati.
La funzionalità utilizza la clipboard di sistema ed è disponibile in tutte le schermate rilevanti.

STATO DEL PROGETTO
Il sistema è stato testato dopo l’aggiornamento dei percorsi, della classe di popolamento e delle dipendenze.
L’applicazione risulta stabile, funzionante e pronta per l’utilizzo o per ulteriori estensioni.

REQUISITI

Java

MySQL

Driver JDBC

Dataset ristoranti aggiornato

Librerie esterne incluse nel progetto (settings.json)

Estensione PlantUML

NOTE FINALI
Il progetto è stato sviluppato con attenzione alla modularità, alla separazione delle responsabilità e alla manutenibilità del codice.
La struttura attuale consente estensioni future senza modifiche invasive ai moduli esistenti
