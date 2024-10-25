
public class WorkerI implements Demo.Worker{
   

    public int countPointsInCircle(int numPoints, com.zeroc.Ice.Current current ) {
        int pointsInCircle = 0;

        for (int i = 0; i < numPoints; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            if (x * x + y * y <= 1) {
                pointsInCircle++;
            }
        }

        return pointsInCircle;
    }
}
