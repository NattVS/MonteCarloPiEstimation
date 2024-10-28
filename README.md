
# Pi Estimation Project with Ice

This project uses [Ice](https://zeroc.com/products/ice) to distribute Pi estimation calculations across several "workers." The client connects to a "master" that coordinates these calculations through multiple instances.

***Participants***

* Mariana Agudelo Salazar
* Julian Motta
* Natalia Vargas

## Prerequisites

Make sure you have the following components installed before proceeding:

1. **Java JDK** (at least version 8 or higher).
2. **Gradle** (for building the project).
3. **ZeroC Ice** (for configuring and running servers and clients).

## Project Setup

1. Clone the project repository:
    ```bash
    git clone <repository-url>
    ```

From the project root /MonteCarloPiEstimation/Montecarlo/...

2. Build the project with Gradle:
    ```bash
    .\gradlew build
    ```

3. Compile the Master, Worker, and Client:
    ```bash
    .\gradlew :server:build
    .\gradlew :worker:build
    .\gradlew :client:build
    ```

## Running the Project

From the project root /MonteCarloPiEstimation/Montecarlo/...

1. Run the Master:

```bash
java -jar master/build/libs/master.jar
```
2. Run the Worker:
   
 ```bash
java -jar worker/build/libs/worker.jar
```
> **Note:** To add more workers, open another terminal and run the same command. Repeat the process for the desired number of workers.

3. Run the Client:
   
 ```bash
java -jar client/build/libs/client.jar
```
