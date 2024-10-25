import Demo.WorkerPrx;

public class Worker {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Crear un adaptador para el worker en un puerto específico

            Demo.Worker worker = new WorkerI();
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("WorkerAdapter", "default -p 10001");
            com.zeroc.Ice.ObjectPrx object  = adapter.add(worker, com.zeroc.Ice.Util.stringToIdentity("Worker1")); 
            adapter.activate(); // Activar el adaptador

            WorkerPrx workerPrx = WorkerPrx.checkedCast(object);

            Demo.MasterPrx masterProxy = Demo.MasterPrx.checkedCast(communicator.stringToProxy("Master:default -p 10000"));

            if (masterProxy != null){
                boolean added = masterProxy.addWorker("Worker1", workerPrx);
                if (added){
                    System.out.println("Worker registrado exitosamente en el Master.");
                } else {
                    System.out.println("No se pudo registrar el Worker en el Master. Ya existe un worker con ese nombre.");
                }
            } else {
                System.out.println("No se pudo conectar al Master.");
            }

            System.out.println("Worker listo y escuchando en el puerto 10001...");
            communicator.waitForShutdown();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
