package server.connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerThreadPool {

    // Pool con 20 thread fissi
    private static final ExecutorService pool = Executors.newFixedThreadPool(20);

    // Cast per accedere a metodi avanzati
    private static ThreadPoolExecutor executor() {
        return (ThreadPoolExecutor) pool;
    }

    // 🔹 Invia un task al pool
    public static void submit(Runnable task) {
        pool.submit(task);
    }

    // 🔹 Numero di thread attivi
    public static int getActiveCount() {
        return executor().getActiveCount();
    }

    // 🔹 Task in coda
    public static int getQueueSize() {
        return executor().getQueue().size();
    }

    // 🔹 Arresto "gentile"
    public static void shutdown() {
        pool.shutdown();
        System.out.println("ThreadPool: shutdown avviato.");
    }

    // 🔹 Arresto immediato
    public static void shutdownNow() {
        pool.shutdownNow();
        System.out.println("ThreadPool: shutdown immediato.");
    }

    // 🔹 Attende la chiusura del pool
    public static boolean awaitTermination(long timeout, TimeUnit unit) {
        try {
            return pool.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
