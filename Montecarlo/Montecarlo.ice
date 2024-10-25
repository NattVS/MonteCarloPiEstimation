module Demo
{
    interface Worker
    {
        int countPointsInCircle(int numPoints);
    }

    interface Master
    {
        double calculatePi(int totalPoints);
        bool addWorker(string name, Worker* w);
    }
}