import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread{
    int w;
    int workers;
    CyclicBarrier barrier;
    Body[] bodies;
    int numberOfBodies;
    int dt;
    Vector forces[];
    Simulate simulation;

    public Worker(int w, int workers, CyclicBarrier barrier, Body[] bodies, int numberOfBodies, int dt, Vector forces[], Simulate simulation) {
        this.w = w;
        this.workers = workers;
        this.barrier = barrier;
        this.bodies = bodies;
        this.numberOfBodies = numberOfBodies;
        this.dt = dt;
        this.forces = forces;
        this.simulation = simulation;

    }

    public void run() {
        System.out.println(Thread.currentThread());
        try {
            simulation.time(w);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

