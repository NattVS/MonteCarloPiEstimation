public class Master {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MasterAdapter", "default -p 10000");
            com.zeroc.Ice.Object object = new MasterI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("Master"));
            adapter.activate();
            
            //connectToWorkers(communicator);
            System.out.println("Master está corriendo y escuchando en el puerto 10000...");
            communicator.waitForShutdown();
        }
    }
}
