module Demo
{
    interface Worker
    {
        ["async"] int countPointsInCircle(int numPoints);
        void ping();
        string getName();
    }

    interface Master
    {
        ["async"] float calculatePi(int totalPoints, bool isTest);
        bool addWorker(string name, Worker* w);
        bool removeWorker(string name);
        int getWorkerCount();
    }
}