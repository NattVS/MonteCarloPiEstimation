import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10000");
            Demo.MasterPrx master = Demo.MasterPrx.checkedCast(base);
            
            if (master == null) {
                throw new Error("Invalid proxy");
            }

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.print("Ingrese la cantidad de puntos para estimar pi: ");
                int totalPoints = scanner.nextInt();

                CompletableFuture<Float> piEstimateFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        return master.calculatePiAsync(totalPoints).get();
                    } catch (Exception e) {
                        System.err.println("Error al calcular pi: " + e.getMessage());
                        return null;
                    }
                });

                piEstimateFuture.thenAccept(piEstimate -> {
                    if (piEstimate != null) {
                        System.out.printf("Estimacion de pi: %.6f%n", piEstimate);
                    } else {
                        System.err.println("No se pudo realizar la estimacion de pi.");
                    }
                    System.out.print("Desea realizar otra estimacion? (s/n): ");
                });
               
                String choice = scanner.next();
                exit = !choice.equalsIgnoreCase("s");
            }
            System.out.println("Saliendo...");
            scanner.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
