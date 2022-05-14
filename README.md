# Simple_OS
Simple emulation to a simple Operating system

This OS includes 
1.an interpreter that reads the txt files and executes their code.
2. Scheduler is responsible for scheduling between the processes in the Ready queue.
3. Memory Management --> the memory is divided into memory words, each word
can store 1 variable and it’s corresponding data.
4. Process Control Block --> In order to schedule your processes, you
will need to keep a PCB for every process. 

The Data inside the PCB includes:
 1. Process ID  
 2. Process State
 3. Program Counter
 4. Memory Boundaries

The OS handles these system calls:
1. Read the data of any file from the disk.
2. Write text output to a file in the disk.
3. Print data on the screen.
4. Take text input from the user.
5. Reading data from memory.
6. Writing data to memory.
