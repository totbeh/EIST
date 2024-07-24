#include <stdio.h>
#include <unistd.h>
#include "include/minheap.h"

// Gloabal Variables
void* proc;

// Structure representing a process
typedef struct Process {
    int id;
    unsigned long runtime;
} Process;

// Function to execute a process
void execute_process(struct Process *process) {
    // Simulate process execution time
    sleep(process->runtime / 10);
    printf("Process %d executed for %lu seconds\n", process->id, process->runtime);
}

Node *parse(int num_processes, char *argv[]) {
    // Allocate memory for the array of processes
    Node *nodes = (Node *) malloc(num_processes);

    // Extract the process ID and runtime from the command-line arguments
    for (int i = 0; i < num_processes; i++) {
        Process *process = (Process *) malloc(sizeof(Process));
        process->id = atoi(argv[2 * i + 1]);
        process->runtime = strtoul(argv[2 * i + 2], NULL, 10);
        nodes[i] = createNode(process->runtime, process);
    }

    return nodes;
}

int main(int argc, char *argv[]) {
    // Calculate the number of processes based on the command-line arguments
    int num_processes = (argc - 1) / 2;
  
    Node *processes = proc = parse(num_processes, argv);
    printf("Starting scheduling\n");
    Heap *hp = createHeap(num_processes, processes);

    // Process the processes in priority order (based on virtual runtime)
    while (1) {
        // Get the process with the highest priority (minimum virtual runtime)
        Node min = extract_min(hp);
        if (min.data == NULL) {
            break;
        }

        execute_process(min.data);
    }

    free(proc);
    
    printf("Printing summary\n");

    for (int i = 0; i < num_processes; i++) {
        Process *process = (Process *) processes[i].data;
        printf("Process[%d] ran for %lu seconds\n", process->id, process->runtime);
        processes[i].data = NULL; 
    }

    free(proc);
    free(processes);
    processes = NULL;

    return 0;
}
