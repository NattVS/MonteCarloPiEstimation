
public class WorkerI implements Demo.Worker{

    private final String name;

    public WorkerI(String name){
        this.name = name;
    }

    public String getName(com.zeroc.Ice.Current current ){
        return name;
    }

    public void ping(com.zeroc.Ice.Current current ){

    }

    public int countPointsInCircle(int numPoints, com.zeroc.Ice.Current current ) {

        System.out.printf("Lanzando %d puntos...%n", numPoints);
        int pointsInCircle = 0;

        for (int i = 0; i < numPoints; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            if (x * x + y * y <= 1) {
                pointsInCircle++;
            }
        }

        System.out.printf("Numero de puntos dentro del circulo: %d%n", pointsInCircle);

        return pointsInCircle;
    }
}
