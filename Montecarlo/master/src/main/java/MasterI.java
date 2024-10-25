import java.util.HashMap;
import java.util.Map;

import Demo.WorkerPrx;

public class MasterI implements Demo.Master {
    private final Map<String, WorkerPrx> workers = new HashMap<>();

    public double calculatePi(int totalPoints, com.zeroc.Ice.Current current ) {
        if (workers.isEmpty()) {
            throw new IllegalStateException("No hay trabajadores disponibles.");
        }

        int numWorkers = workers.size();
        int pointsPerWorker = totalPoints / numWorkers; 
        int totalPointsInCircle = 0;

    
        for (Map.Entry<String, WorkerPrx> entry : workers.entrySet()) {
            WorkerPrx worker = entry.getValue();
            totalPointsInCircle += worker.countPointsInCircle(pointsPerWorker);
        }

        return 4.0 * totalPointsInCircle / totalPoints;
    }

    public boolean addWorker(String name, WorkerPrx w, com.zeroc.Ice.Current current) {
        if (workers.containsKey(name)) {
            return false; 
        }
        workers.put(name, w);
        return true; 
    }
}

