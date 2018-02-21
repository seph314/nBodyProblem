import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread{
    int w;
    int workers;
    CyclicBarrier barrier;
    Body[] bodies;
    int dt;
    Vector forces[];

    public Worker(int w, int workers, CyclicBarrier barrier, Body[] bodies, int dt, Vector forces[]) {
        this.w = w;
        this.workers = workers;
        this.barrier = barrier;
        this.bodies = bodies;
        this.dt = dt;
        this.forces = forces;
    }

    public void run() {
        Simulate simulation = new Simulate(bodies, dt);
        System.out.println(Thread.currentThread());
        try {
            simulation.time(w, workers, barrier, forces);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

