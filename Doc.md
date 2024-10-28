# **Report: Estimation of 𝜋 using the Distributed Monte Carlo Method**

## **Monte Carlo Method**

The Monte Carlo method is a statistical technique used to solve numerical problems through random simulations. For the estimation of π, the method proposes generating random points within a square with side length 2 centered at the origin and evaluating whether they fall inside a circle.

The ratio of points that fall inside the circle to the total number of points thrown is equal to π/4, which allows for the estimation of π. The more points generated, the greater the precision of the π estimation.

π will be estimated using the formula:

$$ \pi \approx 4 \times \frac{Number of points inside the circle}{Total number of point thrown}$$

## **Client-Master-Workers Model**

**Client:** The Client initiates the process and serves as the user interface for the π estimation system. It sends a request to the Master specifying a total number of random points N for the calculation of π using the Monte Carlo Method. Once the Master completes the calculation and obtains the estimation, the Client receives the value of π and displays it to the user.

**Master:** It coordinates the distributed calculation, dividing the load among the workers. Upon receiving the request from the Client with the number N of points, the Master distributes the work among n available Workers, assigning each N/n random points. After collecting the results from each Worker, that is, the number of points that fell inside the circle, the Master calculates the π estimation using the formula described above and sends the final π value to the Client to complete the request.

**Workers:** They are responsible for performing the distributed calculations to estimate π. Each receives an instruction from the Master to generate N/n random points within a square and then calculates how many of those points fall inside the circle inscribed in the square. Once the calculation is completed, they send their result to the Master, indicating the number of points inside the circle. In theory, with a greater number of workers, the system improves in speed and precision by allowing parallel execution and reducing the workload for each individual instance.

## **Task Distribution Strategy Among Workers**

In this implementation, a master-workers approach is used to estimate the value of 𝜋 using the Monte Carlo method. 
The system is composed of a *Master* object and multiple *Worker* objects that register dynamically with the *Master*. Task distribution among the *Workers* is carried out as follows:



![image](https://github.com/user-attachments/assets/ca1fbd55-086f-4873-97a0-1a280f9c39f1)


See: [https://github.com/NattVS/MonteCarloPiEstimation/tree/main/Montecarlo/worker/src/main/java](https://github.com/NattVS/MonteCarloPiEstimation/tree/main/Montecarlo/worker/src/main/java)

1. **Worker Registration:** The *Workers* register with the *Master* using a unique name and a reference to their proxy through the *addWorker* method. This allows the *Master* to maintain a map of all available *Workers* and manage active connections.
2. **Point Distribution:** When the *Master* receives a request to calculate 𝜋 with a total number of points, it first checks the number of registered *Workers*. Then, it divides the workload equally, distributing the same number of points among all the *Workers*.

   Each *Worker* receives a subset of points to simulate using the Monte Carlo method. This is done using each *Worker's* *countPointsInCircle* method, which calculates how many of the randomly generated points fall inside a unit circle.

3. **Asynchronous Execution:** The invocation to the *Workers* is done asynchronously using a linked list: *CompletableFuture*. This allows each *Worker* to process its part of the workload without blocking the *Master's* execution flow. The *Master* waits for all *Workers* to complete their task before collecting the results.
4. **Result Collection:** Once all the *Workers* have finished processing their points, the *Master* collects the results and sums the total number of points that fell inside the circle. With this sum, the *Master* performs the final calculation of 𝜋 using the formula:

$$ \pi \approx 4 \times \frac{Number of points inside the circle}{Total number of point thrown}$$

5. **Failure Handling:** The *Master* periodically checks the status of the *Workers* using the ***ping*** function to ensure their availability. If a *Worker* does not respond, it is removed from the list of active *Workers* and is not considered in the next task distribution. This provides the system with some fault tolerance, allowing the estimation to continue even if one or more *Workers* become unavailable.

This approach of task distribution among all available *Workers* allows for effective load balancing, maximizing resource usage and optimizing calculation time through task parallelization. Additionally, using asynchronous communication prevents the *Master* from being blocked while the *Workers* do their work, thus improving the overall efficiency of the system.

## **Design and Implementation of the Solution Using ICE**

```
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
        ["async"] float calculatePi(int totalPoints);
        bool addWorker(string name, Worker* w);
        bool removeWorker(string name);
        int getWorkerCount();
    }
}
```

The π estimation solution using the Monte Carlo method was designed following a distributed client-server architecture using Zeroc ICE, which provides a framework for creating distributed applications that facilitates communication between components through clearly defined interfaces. The solution consists of two main roles: *Master* and *Workers*, which interact with each other to perform the estimation. Below is a detailed design of the solution:

1. **Definition of Interfaces with ICE:**  
   The interfaces for communication between the *Master* and the *Workers* are defined in the ***Montecarlo.ice*** file:
   1. ***Worker***: Each *Worker* instance is responsible for receiving a number of points, simulating their random throws, and returning how many fall inside a unit circle. The `Worker` interface exposes the methods:
       1. *countPointsInCircle(int numPoints)*: Asynchronous method that receives a number of points and returns how many fall inside the circle.
       2. *ping():* Method used to check if the *Worker* is active.
       3. *getName():* Returns the name of the *Worker*.
   2. ***Master***: The *Master* coordinates the estimation of 𝜋 by distributing tasks among registered *Workers*. The `Master` interface exposes the methods:
       4. *calculatePi(int totalPoints):* Asynchronous method that calculates 𝜋 by dividing the workload among available *Workers*.
       5. *addWorker(string name, Worker* w):* Registers a new *Worker* in the system.
       6. *removeWorker(string name):* Removes a *Worker* from the system when it becomes unavailable.
       7. *getWorkerCount():* Returns the number of currently registered *Workers*.
2. **Implementation of *Master* and *Workers*:**
   3. **Implementation of *Worker***: The *WorkerI* class implements the *Demo.Worker* interface and is responsible for performing the Monte Carlo simulation. Each time it receives a request through *countPointsInCircle*, it generates random points and determines how many of them fall inside the circle. The implementation uses the *CompletableFuture* class to handle asynchronous calls, allowing processing to be done concurrently without blocking execution.
   4. **Implementation of *Master*:** The *MasterI* class implements the *Demo.Master* interface and manages the task distribution logic. Upon receiving a request to calculate 𝜋 through the *calculatePi* method, the *Master* divides the points among the registered *Workers* and collects results asynchronously using futures. To manage the connection and availability of the *Workers*, the *Master* implements a monitoring system through the *ping* method, ensuring that only active *Workers* are used for calculations.
3. **Communication Setup:**  
   The communication between the *Master* and the *Workers* is based on proxies generated from the interfaces defined in *Montecarlo.ice*. Each *Worker* registers with the *Master* using its proxy, configured with a unique name and a specific port. The *Master* maintains a map of *Worker* proxies, allowing it to invoke their methods remotely to distribute the workload.
   5. The *Master* configuration defines an adapter that listens on a specific port to receive requests from the *

Client* for 𝜋 estimation. The *Master* proxy is distributed among *Clients* so that they can initiate the calculation request.

## **Results**

## **Performance analysis as a function of the number of nodes and points used**
