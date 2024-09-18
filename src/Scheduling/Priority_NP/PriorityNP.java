package Scheduling.Priority_NP;
import java.util.*;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int priority;
    int completion_time;
    int turn_around_time;
    int waiting_time;

    Process() {}

    Process(String pid, int ar, int br, int pr) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        priority = pr;
    }
}

public class PriorityNP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number of Processes: ");
        int n = sc.nextInt();

        Process[] process_queue = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for Process " + (i + 1) + " : ");
            String pid = sc.next();
            System.out.print("Enter Arrival Time for Process " + (i + 1) + " : ");
            int ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + " : ");
            int br = sc.nextInt();
            System.out.print("Enter Priority for Process " + (i + 1) + " : ");
            int pr = sc.nextInt();

            process_queue[i] = new Process(pid, ar, br, pr);
        }

        // Sort processes based on priority (lower number -> higher priority)
        for (int i = 0; i < n; i++) {
            int pos = i;
            for (int j = i + 1; j < n; j++) {
                if (process_queue[j].priority < process_queue[pos].priority) {
                    pos = j;
                }
            }
            Process temp = process_queue[i];
            process_queue[i] = process_queue[pos];
            process_queue[pos] = temp;
        }

        // Calculate completion, turnaround, and waiting times
        float total_tat = 0, total_wt = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
            } else {
                if (process_queue[i].arrival_time > process_queue[i - 1].completion_time) {
                    process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
                } else {
                    process_queue[i].completion_time = process_queue[i - 1].completion_time + process_queue[i].burst_time;
                }
            }
            process_queue[i].turn_around_time = process_queue[i].completion_time - process_queue[i].arrival_time;
            process_queue[i].waiting_time = process_queue[i].turn_around_time - process_queue[i].burst_time;

            total_tat += process_queue[i].turn_around_time;
            total_wt += process_queue[i].waiting_time;
        }

        // Print process details
        System.out.print("-------------------------------------------------------------------------------------------------------------------");
        System.out.print("\nProcess\t\tArrival Time\tBurst Time\tPriority\tCompletion Time\t\tTurnaround Time\t\tWaiting Time\n");
        System.out.print("-------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("\n  " + process_queue[i].id + "\t\t" + process_queue[i].arrival_time + "\t\t" + process_queue[i].burst_time +
                    "\t\t" + process_queue[i].priority + "\t\t" + process_queue[i].completion_time + "\t\t\t" + process_queue[i].turn_around_time +
                    "\t\t\t" + process_queue[i].waiting_time);
        }

        // Print average times
        System.out.println("Average Turn Around Time = " + (total_tat / n));
        System.out.println("Average Waiting Time = " + (total_wt / n));

        sc.close();
    }
}
