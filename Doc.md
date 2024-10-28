# **Report: Estimation of 𝜋 using the Distributed Monte Carlo Method**

## **Monte Carlo Method**

The Monte Carlo method is a statistical technique used to solve numerical problems through random simulations. For the estimation of π, the method proposes generating random points within a square with side length 2 centered at the origin and evaluating whether they fall inside a circle.

The ratio of points that fall inside the circle to the total number of points thrown is equal to π/4, which allows for the estimation of π. The more points generated, the greater the precision of the π estimation.

π will be estimated using the formula:

$$ \pi \approx 4 \times \frac{Number \: of \: points \: inside \: the \:circle}{Total \: number \: of \: point \: thrown}$$

## **Client-Master-Workers Model**

**Client:** The Client initiates the process and serves as the user interface for the π estimation system. It sends a request to the Master specifying a total number of random points N for the calculation of π using the Monte Carlo Method. Once the Master completes the calculation and obtains the estimation, the Client receives the value of π and displays it to the user.



**Master:** It coordinates the distributed calculation, dividing the load among the workers. Upon receiving the request from the Client with the number N of points, the Master distributes the work among n available Workers, assigning each N/n random points. After collecting the results from each Worker, that is, the number of points that fell inside the circle, the Master calculates the π estimation using the formula described above and sends the final π value to the Client to complete the request.



**Workers:** They are responsible for performing the distributed calculations to estimate π. Each receives an instruction from the Master to generate N/n random points within a square and then calculates how many of those points fall inside the circle inscribed in the square. Once the calculation is completed, they send their result to the Master, indicating the number of points inside the circle. In theory, with a greater number of workers, the system improves in speed and precision by allowing parallel execution and reducing the workload for each individual instance.



## **Task Distribution Strategy Among Workers**



In this implementation, a master-workers approach is used to estimate the value of 𝜋 using the Monte Carlo method. 
The system is composed of a *Master* object and multiple *Worker* objects that register dynamically with the *Master*. Task distribution among the *Workers* is carried out as follows:



![image](https://github.com/user-attachments/assets/ca1fbd55-086f-4873-97a0-1a280f9c39f1)


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
        ["async"] float calculatePi(int totalPoints, bool isTest);
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
       * *countPointsInCircle(int numPoints)*: Asynchronous method that receives a number of points and returns how many fall inside the circle.
       * *ping():* Method used to check if the *Worker* is active.
       * *getName():* Returns the name of the *Worker*.
   2. ***Master***: The *Master* coordinates the estimation of 𝜋 by distributing tasks among registered *Workers*. The `Master` interface exposes the methods:
       * *calculatePi(int totalPoints):* Asynchronous method that calculates 𝜋 by dividing the workload among available *Workers*.
       * *addWorker(string name, Worker* w):* Registers a new *Worker* in the system.
       * *removeWorker(string name):* Removes a *Worker* from the system when it becomes unavailable.
       * *getWorkerCount():* Returns the number of currently registered *Workers*.
          
2. **Implementation of *Master* and *Workers*:**
   * **Implementation of *Worker***: The *WorkerI* class implements the *Demo.Worker* interface and is responsible for performing the Monte Carlo simulation. Each time it receives a request through *countPointsInCircle*, it generates random points and determines how many of them fall inside the circle. The implementation uses the *CompletableFuture* class to handle asynchronous calls, allowing processing to be done concurrently without blocking execution.
   * **Implementation of *Master*:** The *MasterI* class implements the *Demo.Master* interface and manages the task distribution logic. Upon receiving a request to calculate 𝜋 through the *calculatePi* method, the *Master* divides the points among the registered *Workers* and collects results asynchronously using futures. To manage the connection and availability of the *Workers*, the *Master* implements a monitoring system through the *ping* method, ensuring that only active *Workers* are used for calculations.

     
3. **Communication Setup:**  
   The communication between the *Master* and the *Workers* is based on proxies generated from the interfaces defined in *Montecarlo.ice*. Each *Worker* registers with the *Master* using its proxy, configured with a unique name and a specific port. The *Master* maintains a map of *Worker* proxies, allowing it to invoke their methods remotely to distribute the workload.
   * The *Master* configuration defines an adapter that listens on a specific port to receive requests from the *Client* for 𝜋 estimation. The *Master* proxy is distributed among *Clients* so that they can initiate the calculation request.

## **Results**
See [https://github.com/NattVS/MonteCarloPiEstimation/blob/main/Montecarlo/resultados.txt](Results.txt)


**Test 1**  
**Inputs**: 100 points, 1 worker.  
**Outputs**:  
Estimated π: 3.046  
Execution time: 2.55 ms  
Error: 0.10497538  
**Performance Analysis**: With a single worker and a small sample size, the execution time is minimal. However, the estimated value has a higher error due to the limited number of points.

---

**Test 2**  
**Inputs**: 1000 points, 1 worker.  
**Outputs**:  
Estimated π: 3.085  
Execution time: 1.75 ms  
Error: 0.0561018  
**Performance Analysis**: Increasing the number of points improves the accuracy slightly, though the time remains low. With a single worker, the process remains efficient but still has some variability in accuracy.

---

**Test 3**  
**Inputs**: 10,000 points, 1 worker.  
**Outputs**:  
Estimated π: 3.135  
Execution time: 2.43 ms  
Error: 0.00594455  
**Performance Analysis**: At this point count, the estimation becomes more accurate with a slightly longer time, yet still very efficient with a single worker.

---

**Test 4**  
**Inputs**: 100,000 points, 1 worker.  
**Outputs**:  
Estimated π: 3.139  
Execution time: 7.49 ms  
Error: 0.002489984  
**Performance Analysis**: As the number of points increases, the time also increases, but the estimation becomes more precise.

---

**Test 5**  
**Inputs**: 1,000,000 points, 1 worker.  
**Outputs**:  
Estimated π: 3.140  
Execution time: 49.32 ms  
Error: 0.0011368692  
**Performance Analysis**: The estimation of π is highly accurate with a large point count, though the single worker takes a significantly longer time to process.

---

**Test 6**  
**Inputs**: 10,000,000 points, 1 worker.  
**Outputs**:  
Estimated π: 3.141  
Execution time: 511.89 ms  
Error: 3.170967E-4  
**Performance Analysis**: With a very high point count, the accuracy becomes extremely precise, but the time required also increases substantially with a single worker.

---

**Test 7**  
**Inputs**: 100 points, 5 workers.  
**Outputs**:  
Estimated π: 3.019  
Execution time: 5.19 ms  
Error: 0.12169136  
**Performance Analysis**: Using 5 workers increases the time compared to a single worker due to parallelization overhead with small point counts. Accuracy remains low with fewer points.

---

**Test 8**  
**Inputs**: 1000 points, 5 workers.  
**Outputs**:  
Estimated π: 3.107  
Execution time: 3.82 ms  
Error: 0.034499973  
**Performance Analysis**: The time is slightly higher than with a single worker, but the error decreases, showcasing the benefits of parallelization as point counts increase.

---

**Test 9**  
**Inputs**: 10,000 points, 5 workers.  
**Outputs**:  
Estimated π: 3.127  
Execution time: 4.55 ms  
Error: 0.014651865  
**Performance Analysis**: Parallelization with 5 workers starts to show efficiency, offering a balance between speed and accuracy.

---

**Test 10**  
**Inputs**: 100,000 points, 5 workers.  
**Outputs**:  
Estimated π: 3.136  
Execution time: 8.28 ms  
Error: 0.005723208  
**Performance Analysis**: The time increases slightly compared to a single worker, but the reduction in error is noticeable, making parallel processing effective for larger datasets.

---

**Test 11**  
**Inputs**: 1,000,000 points, 5 workers.  
**Outputs**:  
Estimated π: 3.141  
Execution time: 17.94 ms  
Error: 9.4237924E-4  
**Performance Analysis**: With more points, 5 workers provide a good trade-off between accuracy and speed, significantly reducing the error.

---

**Test 12**  
**Inputs**: 10,000,000 points, 5 workers.  
**Outputs**:  
Estimated π: 3.141  
Execution time: 156.06 ms  
Error: 5.9232116E-4  
**Performance Analysis**: The parallel approach helps maintain high precision while reducing processing time compared to a single worker.

---

**Test 13**  
**Inputs**: 100 points, 10 workers.  
**Outputs**:  
Estimated π: 2.98  
Execution time: 6.80 ms  
Error: 0.16133738  
**Performance Analysis**: Using 10 workers with a very small point count results in higher overhead, leading to longer time and significant estimation errors.

---

**Test 14**  
**Inputs**: 1000 points, 10 workers.  
**Outputs**:  
Estimated π: 3.103  
Execution time: 20.13 ms  
Error: 0.036499977  
**Performance Analysis**: While time increases due to worker coordination, the accuracy begins to improve as more points are processed.

---

**Test 15**  
**Inputs**: 10,000 points, 10 workers.  
**Outputs**:  
Estimated π: 3.123  
Execution time: 6.30 ms  
Error: 0.018101782  
**Performance Analysis**: The process balances time and precision, but with diminishing returns as workers increase.

---

**Test 16**  
**Inputs**: 100,000 points, 10 workers.  
**Outputs**:  
Estimated π: 3.137  
Execution time: 8.41 ms  
Error: 0.0043099523  
**Performance Analysis**: Parallelization becomes more effective, showing reduced error and maintaining relatively low execution time.

---

**Test 17**  
**Inputs**: 1,000,000 points, 10 workers.  
**Outputs**:  
Estimated π: 3.141  
Execution time: 26.50 ms  
Error: 0.0011334419  
**Performance Analysis**: The time required increases but so does the precision, showing the benefits of parallel processing for larger point sets.

---

**Test 18**  
**Inputs**: 10,000,000 points, 10 workers.  
**Outputs**:  
Estimated π: 3.141  
Execution time: 159.17 ms  
Error: 2.7945638E-4  
**Performance Analysis**: With 10 workers and a large point set, the process achieves highly accurate results, with time increasing proportionally to the workload.


## **Performance analysis as a function of the number of nodes and points used**

To analyze the results of your program, we can focus on how the execution time and error vary with the number of points and workers. Here's how we can structure the analysis:

### Results Analysis
The performance of the Monte Carlo method for estimating π in a distributed system depends on two key parameters: the number of points used for the simulation and the number of worker nodes. This section analyzes how these parameters affect the accuracy (error) and execution time of the program.

#### 1. Impact of Number of Points
- **Increasing the number of points**: As the number of points increases, the estimation of π becomes more accurate, as seen by the decreasing error values. For instance:
  - With 100 points and 1 worker, the error is 0.10497538, indicating a low accuracy due to the small sample size.
  - At 1,000,000 points, the error reduces significantly to 0.0011368692, reflecting a better approximation of π.
  - Similarly, at 10,000,000 points, the error further decreases to 3.170967E-4, showing the convergence of the estimate toward the actual value of π.

- **Execution time trend**: As the number of points increases, the execution time also increases, but the improvement in accuracy makes the time investment worthwhile:
  - For 1,000 points, the execution time with 1 worker is 1,750,012 ns.
  - For 1,000,000 points, the time increases to 49,316,537 ns, and for 10,000,000 points, it reaches 511,888,637 ns.

#### 2. Impact of Number of Workers
- **Effect of parallelization**: Increasing the number of workers generally helps in reducing execution time for larger point sets, but the benefit varies:
  - With 1 worker and 1,000,000 points, the execution time is 49,316,537 ns.
  - With 5 workers for the same number of points, the time reduces to 17,941,737 ns, showing a notable improvement.
  - With 10 workers, the time increases slightly to 26,496,200 ns, indicating that communication overhead and coordination costs can start to outweigh the parallelization benefits.

- **Accuracy with more workers**: Increasing the number of workers doesn't directly affect the accuracy, as the error is more related to the number of points. However, parallelization helps distribute the computational load for larger point sets, improving efficiency:
  - With 5 workers and 10,000,000 points, the error is 5.9232116E-4.
  - With 10 workers, the error further decreases to 2.7945638E-4, as the larger number of points still leads to a better approximation of π.

#### 3. Trade-offs Between Time and Accuracy
- For smaller point counts, using more workers doesn't yield a significant time advantage and introduces communication overhead, which can increase execution time.
- For larger point sets, the use of multiple workers becomes more effective, as it balances the computation load among nodes, reducing the time required for calculation.
- There is a point of diminishing returns: beyond a certain number of workers, the overhead of managing communication outweighs the benefits of parallel execution, as seen with 10 workers and 1,000,000 points.

### General Analysis
The results indicate that the Monte Carlo method's accuracy increases with more points, but this comes with a cost in terms of processing time. Using multiple workers helps reduce execution time for large point counts, making the distributed system more efficient. However, when the number of workers increases too much relative to the workload, the communication overhead can negate the gains from parallel computation. Thus, finding the right balance between the number of points and workers is crucial for optimizing both performance and accuracy.
