package Scheduling.SJF_Preemptive;
import java.util.Scanner;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int completion_time;
    int turn_around_time;
    int waiting_time;
    int remaining_time;
    boolean isCompleted;

    Process(String pid, int ar, int br) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        remaining_time = br;
        isCompleted = false;
    }
}

public class PreemptiveSJF {
    public static void main(String[] args) {
        String id;
        int n,at,bt,min_burst_time,min_burst_index,total_completed=0,current_time = 0;
        float avg_tat=0, avg_wt=0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number of Processes : ");
        n = sc.nextInt();
        Process[] p_queue = new Process[n];
        for(int i=0;i<n;i++) {
            System.out.print("Enter Process ID for Process " + (i+1) + " : ");
            id = sc.next();
            System.out.print("Enter Arrival Time : ");
            at = sc.nextInt();
            System.out.print("Enter Burst Time : ");
            bt = sc.nextInt();
            p_queue[i] = new Process(id,at,bt);
        }
        while(total_completed < n) {
            min_burst_index = n;
            min_burst_time = Integer.MAX_VALUE;
            for(int i=0;i<n;i++) {
                if(p_queue[i].arrival_time <= current_time && !p_queue[i].isCompleted && p_queue[i].remaining_time < min_burst_time) {
                    min_burst_time = p_queue[i].remaining_time;
                    min_burst_index = i;
                }
            }
            if(min_burst_index!=n)
                p_queue[min_burst_index].remaining_time--;
            current_time++;
            if(p_queue[min_burst_index].remaining_time == 0) {
                p_queue[min_burst_index].completion_time = current_time;
                p_queue[min_burst_index].turn_around_time = p_queue[min_burst_index].completion_time - p_queue[min_burst_index].arrival_time;
                p_queue[min_burst_index].waiting_time = p_queue[min_burst_index].turn_around_time - p_queue[min_burst_index].burst_time;
                p_queue[min_burst_index].isCompleted = true;
                total_completed++;
                avg_tat += p_queue[min_burst_index].turn_around_time;
                avg_wt += p_queue[min_burst_index].waiting_time;
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
