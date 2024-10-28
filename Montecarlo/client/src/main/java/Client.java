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
                System.out.println("Seleccione el modo de operacion:");
                System.out.println("1. Estimacion normal");
                System.out.println("2. Modo prueba");
                System.out.print("Ingrese su opcion (1/2): ");
                int option = scanner.nextInt();

                if (option == 1) {
                    // Estimación normal
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

                    piEstimateFuture.join();

                    piEstimateFuture.thenAccept(piEstimate -> {
                        if (piEstimate != null) {
                            System.out.printf("Estimacion de pi: %.6f%n", piEstimate);
                        } else {
                            System.err.println("No se pudo realizar la estimación de pi.");
                        }
                    });
                    
                } else if (option == 2) {
                   
                    int[] testPoints = {100, 1000, 10000, 100000, 1000000};

                    for (int points : testPoints) {
                        for (int i = 0; i < 10; i++) {
                            CompletableFuture<Float> piEstimateFuture = CompletableFuture.supplyAsync(() -> {
                                try {
                                    return master.calculatePiAsync(points).get();
                                } catch (Exception e) {
                                    return null;
                                }
                            });

                            piEstimateFuture.join();
                        }
                    }
                    System.out.println("Modo prueba ejecutado.");
                } else {
                    System.out.println("Opcion no valida. Por favor, elija 1 o 2.");
                }

                System.out.print("Desea realizar otra operacion? (s/n): ");
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
