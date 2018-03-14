
public class Worker extends Thread {
    private int w;
    Body[] bodies;
    int dt;
    private SimulateN2Parallel simulation;

    Worker(int w, Body[] bodies, int dt, SimulateN2Parallel simulation) {
        this.w = w;
        this.bodies = bodies;
        this.dt = dt;
        this.simulation = simulation;

    }

    public void run() {
        try {
            simulation.time(w);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

