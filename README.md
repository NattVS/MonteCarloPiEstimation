# Proyecto de Estimación de Pi con Ice

Este proyecto utiliza [Ice](https://zeroc.com/products/ice) para distribuir cálculos de estimación de Pi entre varios "workers". El cliente se conecta a un "master" que coordina estos cálculos a través de múltiples instancias.

## Prerrequisitos

Asegúrate de tener instalados los siguientes componentes antes de proceder:

1. **Java JDK** (al menos versión 8 o superior).
2. **Gradle** (para compilar el proyecto).
3. **ZeroC Ice** (para configuración y ejecución de servidores y clientes).

## Configuración del Proyecto

1. Clona el repositorio del proyecto:
    ```bash
    git clone <url-del-repositorio>
    ```

Desde la raíz del proyecto /MonteCarloPiEstimation/Montecarlo/...

2. Construye el proyecto con Gradle:
    ```bash
    .\gradlew build
    ```

3. Compila el Master, el Worker y el Cliente
    ```bash
    .\gradlew :server:build
    .\gradlew :worker:build
    .\gradlew :client:build
    ```
## Ejecución del Proyecto

Desde la raíz del proyecto /MonteCarloPiEstimation/Montecarlo/...

1. Ejecuta el Master

```bash
java -jar master/build/libs/master.jar
```
2. Ejectura el Worker
   
 ```bash
java -jar worker/build/libs/worker.jar
```
> **Nota:** Para añadir más workers abre otra terminal y ejecuta el mismo comando. Repite el proceso para la cantidad de workers que desees.

3. Ejecuta el Cliente
   
 ```bash
java -jar client/build/libs/client.jar
```



