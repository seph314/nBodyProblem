import java.util.concurrent.CyclicBarrier;

public class InitiateN2Parallel {
    private Body[] bodies;
    private int dt;
    private int numberOfBodies;
    private int workers;
    private Vector[] forces;
    private CyclicBarrier barrier;

    public InitiateN2Parallel(Body[] bodies, int dt, int numberOfBodies, int workers, Vector[] forces) {
        this.bodies = bodies;
        this.dt = dt;
        this.numberOfBodies = numberOfBodies;
        this.workers = workers;
        this.forces = forces;
        barrier = new CyclicBarrier(workers);
    }

    public void initiate(){
        Simulate simulation = new Simulate(workers, barrier, bodies, numberOfBodies, dt, forces);
        int w = 0;
        Worker worker[] = new Worker[workers];
        for(int i = 0; i < workers; i++){
            System.out.println("w = " + w);
            worker[i] = new Worker(w, workers, barrier,  bodies, numberOfBodies, dt, forces, simulation);
            w += (numberOfBodies/workers);
            worker[i].start();
        }
        for(int i = 0; i < workers; i++)
            try {
                worker[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
