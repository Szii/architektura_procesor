# SLDR II emulator

Project for implementing processor emulator based on SLDR II ISA.

## How to run?

Prerequsity is JDK 17 or newer.

The program is meant to be ran once, where it takes input as machine code and outputs instruction cycle steps into console.
```
mvn compile
```
```
java -jar architektura_procesor-1.0.jar
```
The path for file containing machine code can be set through environmental variable 
```
MEMORY_PATH - Relative path to file. Default is ./bytes_1.txt
```

