package common;

public enum MessageType {

    // UTENTE
    LOGIN,
    REGISTRAZIONE,

    // RISTORANTI MICHELIN (sola lettura)
    CERCA_RISTORANTI,
    VISUALIZZA,                     // 🔥 usato per i dettagli ristorante

    // RISTORANTI UTENTE (CRUD)
    AGGIUNGI_RISTORANTE,
    MODIFICA_RISTORANTE,
    ELIMINA_RISTORANTE,
    VISUALIZZA_RIEPILOGO_GESTORE,
    VISUALIZZA_UTENTE,              // 🔥 usato per i dettagli lato utente loggato

    // RECENSIONI
    AGGIUNGI_RECENSIONE,
    MODIFICA_RECENSIONE,
    ELIMINA_RECENSIONE,
    VISUALIZZA_RECENSIONI_ANONIME,  // 🔥 usato dal tuo controller
    VISUALIZZA_RECENSIONI_GESTORE,
    RISPONDI_RECENSIONE,

    // PREFERITI
    AGGIUNGI_PREFERITO,
    RIMUOVI_PREFERITO,
    VISUALIZZA_PREFERITI
}
