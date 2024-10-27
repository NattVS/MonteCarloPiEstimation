import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import Demo.WorkerPrx;
import java.util.Scanner;

public class Worker {

    private static Communicator communicator;
    private static Demo.MasterPrx masterProxy;

    public static void main(String[] args) {
        try {

            communicator = Util.initialize(args);
            masterProxy = Demo.MasterPrx.checkedCast(communicator.stringToProxy("Master:default -p 10000"));

            int workerCount = masterProxy.getWorkerCount() + 1;
            int port = 10000 + workerCount;
            String name = "Worker" + workerCount;

            Demo.Worker worker = new WorkerI(name);
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("WorkerAdapter", "default -p " + port);
            com.zeroc.Ice.ObjectPrx object = adapter.add(worker, com.zeroc.Ice.Util.stringToIdentity(name)); 
            adapter.activate();

            WorkerPrx workerPrx = WorkerPrx.checkedCast(object);

            if (masterProxy.addWorker(name, workerPrx)){
                System.out.println(name + " registrado exitosamente en el Master.");
                System.out.println(name + " listo y escuchando en el puerto " + port + "..."); 
            }

            Thread scannerThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Escriba exit si desea finalizar la ejecucion del worker.");
                while (true) {
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input)) {
                        try {
                            if (masterProxy != null) {
                                masterProxy.removeWorker(name);
                            }
                            System.out.println("Worker eliminado. Saliendo...");
                            communicator.close();
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                scanner.close(); 
            });
            scannerThread.start();
            
            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
