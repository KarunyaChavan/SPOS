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
    boolean isCompleted = false;

    Process(String pid, int ar, int br, int pr) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        priority = pr;
    }
}

public class PriorityNP {
    public static void main(String[] args) {
        String id;
        int n,at,bt,pr,prior,total_completed=0,current_time = 0;
        float avg_tat=0, avg_wt=0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number of Processes : ");
        n = sc.nextInt();
        Process[] p_queue = new Process[n];
        Process curr_process;
        for(int i=0;i<n;i++) {
            System.out.print("Enter Process ID for Process " + (i+1) + " : ");
            id = sc.next();
            System.out.print("Enter Arrival Time : ");
            at = sc.nextInt();
            System.out.print("Enter Burst Time : ");
            bt = sc.nextInt();
            System.out.print("Enter Priority : ");
            pr = sc.nextInt();
            p_queue[i] = new Process(id,at,bt,pr);
        }
        while(total_completed < n) {
            prior = Integer.MAX_VALUE;
            curr_process = null;
            for(Process p: p_queue) {
                //Condition
                if(!p.isCompleted && p.arrival_time <= current_time && p.priority < prior) {
                    prior = p.priority;
                    curr_process = p;
                }
            }
            if(curr_process!=null) {
                curr_process.completion_time = current_time + curr_process.burst_time;
                curr_process.turn_around_time = curr_process.completion_time - curr_process.arrival_time;
                curr_process.waiting_time = curr_process.turn_around_time - curr_process.burst_time;
                avg_tat += curr_process.turn_around_time;
                avg_wt += curr_process.waiting_time;
                curr_process.isCompleted = true;
                total_completed++;
                current_time = curr_process.completion_time;
            }else {
                current_time++;
            }
        }
        avg_tat /= n;
        avg_wt /= n;
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Process\t\tArrival Time\tBurst Time\tCompletion Time\t\tTurnAround Time\t\tWaiting Time");
        System.out.println("------------------------------------------------------------------------------------------------------");
        for(int i=0;i<n;i++) {
            System.out.println("  "+p_queue[i].id + "\t\t\t" + p_queue[i].arrival_time + "\t\t\t" + p_queue[i].burst_time + "\t\t" + p_queue[i].completion_time + "\t\t\t" + p_queue[i].turn_around_time + "\t\t\t" + p_queue[i].waiting_time);
        }
        System.out.println("Average TAT : " + avg_tat);
        System.out.println("Average WT : " + avg_wt);
    }
}
