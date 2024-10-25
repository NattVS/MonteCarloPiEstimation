import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            // Crear el proxy para el Maestro
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10000");
            Demo.MasterPrx master = Demo.MasterPrx.checkedCast(base);
            if (master == null) {
                throw new Error("Invalid proxy");
            }

            // Definir el número total de puntos y la cantidad de trabajadores
            int totalPoints = 1000000; // Cambia este valor según sea necesario

            try {
                double piEstimate = master.calculatePi(totalPoints);
                System.out.printf("Estimación de π: %.6f%n", piEstimate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            

            // Imprimir el resultado
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
