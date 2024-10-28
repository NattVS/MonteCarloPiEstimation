import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import Demo.WorkerPrx;

public class MasterI implements Demo.Master {
    
    private final Map<String, WorkerPrx> workers = new HashMap<>();
    private final Timer timer;

    public MasterI() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkWorkersStatus();
            }
        }, 0, 500);
    }

    private void checkWorkersStatus() {
        synchronized (workers) {
            for (String workerId : workers.keySet()) {
                WorkerPrx worker = workers.get(workerId);
                if (worker == null || !isWorkerAlive(worker, workerId)) {
                    removeWorker(workerId, null);
                }
            }
        }
    }

    private boolean isWorkerAlive(WorkerPrx worker, String workerId) {
        try {
            worker.ping();
            return true;
        } catch (Exception e) {
            System.out.println(workerId + " no responde.");
            return false;
        }
    }

    public int getWorkerCount(com.zeroc.Ice.Current current) {
        return workers.size();
    }

    public float calculatePi(int numPoints, com.zeroc.Ice.Current current) {
        int numWorkers = workers.size();
        if (numWorkers == 0) {
            System.out.println("No hay workers registrados para realizar la estimacion.");
            return 0.0f;
        }


        System.out.printf("Estimando pi con %d puntos y %d trabajadores...%n", numPoints, numWorkers);
        int pointsPerWorker = numPoints / numWorkers;

        long startTime = System.nanoTime();  

        List<CompletableFuture<Integer>> futures = createWorkerTasks(pointsPerWorker);
        int totalPointsInCircle = gatherResults(futures);

        long endTime = System.nanoTime();  // Finalizar tiempo
        long duration = endTime - startTime;  // Calcular duración en nanosegundos

        if (totalPointsInCircle < 0) {
            return 0.0f;
        }

        writeResultsToFile(numPoints, numWorkers, duration);

        float estimation =  4.0f * totalPointsInCircle / numPoints;
        System.out.println("Estimacion final: "+ estimation);
        System.out.println("--------------------------------------");
        return estimation;
    }

   private void writeResultsToFile(int numPoints, int numWorkers, long duration) {
        String filePath = "resultados.txt";
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al verificar/crear el archivo: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Puntos: " + numPoints + ", Trabajadores: " + numWorkers + ", Tiempo: " + (duration / 1_000_000) + " ms");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }       

    private List<CompletableFuture<Integer>> createWorkerTasks(int pointsPerWorker) {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();

        for (Map.Entry<String, WorkerPrx> entry : workers.entrySet()) {
            String workerName = entry.getKey();
            WorkerPrx worker = entry.getValue();
            System.out.println("Solicitando al worker " + workerName + " calcular " + pointsPerWorker + " puntos...");
            CompletableFuture<Integer> future = worker.countPointsInCircleAsync(pointsPerWorker);
            futures.add(future);
        }

        return futures;
    }
    
    private int gatherResults(List<CompletableFuture<Integer>> futures) {
        int totalPointsInCircle = 0;
        try {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();
            for (CompletableFuture<Integer> future : futures) {
                totalPointsInCircle += future.get();
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error al obtener resultados: " + e.getMessage());
            return -1;
        }
        return totalPointsInCircle;
    }
    

    public boolean addWorker(String name, WorkerPrx w, com.zeroc.Ice.Current current) {
        if (workers.containsKey(name)) {
            return false; 
        }
        workers.put(name, w);
        System.out.println(name + " registrado exitosamente.");
        return true; 
    }

    public boolean removeWorker(String name, com.zeroc.Ice.Current current) {
        if (workers.remove(name) != null) {
            System.out.println("Worker " + name + " eliminado.");
            return true;
        } else {
            System.out.println("Worker " + name + " no encontrado.");
            return false;
        }
    }
}

