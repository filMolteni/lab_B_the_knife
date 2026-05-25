package server.connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Gestisce il pool di thread utilizzato dal server per eseguire i vari
 * {@link Runnable} associati ai client connessi.
 *
 * Il server utilizza un pool fisso di 20 thread per garantire:
 * - gestione concorrente di più client
 * - controllo del carico
 * - riutilizzo dei thread per evitare overhead di creazione
 *
 * La classe fornisce metodi per:
 * - inviare task al pool
 * - monitorare lo stato del pool (thread attivi, coda)
 * - chiudere il pool in modo ordinato o immediato
 */
public class ServerThreadPool {

    /** Pool con 20 thread fissi */
    private static final ExecutorService pool = Executors.newFixedThreadPool(20);

    /**
     * Restituisce il pool come ThreadPoolExecutor per accedere a metodi avanzati.
     *
     * @return istanza di ThreadPoolExecutor
     */
    private static ThreadPoolExecutor executor() {
        return (ThreadPoolExecutor) pool;
    }

    /**
     * Invia un task al thread pool per l'esecuzione.
     *
     * @param task operazione da eseguire in un thread del pool
     */
    public static void submit(Runnable task) {
        pool.submit(task);
    }

    /**
     * Restituisce il numero di thread attualmente attivi.
     *
     * @return numero di thread in esecuzione
     */
    public static int getActiveCount() {
        return executor().getActiveCount();
    }

    /**
     * Restituisce il numero di task in coda in attesa di essere eseguiti.
     *
     * @return dimensione della coda del pool
     */
    public static int getQueueSize() {
        return executor().getQueue().size();
    }

    /**
     * Arresta il pool in modo "gentile":
     * - non accetta nuovi task
     * - completa quelli già in coda
     */
    public static void shutdown() {
        pool.shutdown();
        System.out.println("ThreadPool: shutdown avviato.");
    }

    /**
     * Arresto immediato del pool:
     * - interrompe i task in esecuzione
     * - svuota la coda
     */
    public static void shutdownNow() {
        pool.shutdownNow();
        System.out.println("ThreadPool: shutdown immediato.");
    }

    /**
     * Attende la chiusura del pool per un certo periodo.
     *
     * @param timeout tempo massimo di attesa
     * @param unit unità di misura del timeout
     * @return true se il pool si è chiuso entro il timeout
     */
    public static boolean awaitTermination(long timeout, TimeUnit unit) {
        try {
            return pool.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
