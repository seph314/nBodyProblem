import java.util.concurrent.CyclicBarrier;

public class InitiateN2Parallel {
    private Body[] bodies;
    private int dt;
    private int numberOfBodies;
    private int workers;
    private Vector[] forces;
    private CyclicBarrier barrier;
    private int numSteps;

    public InitiateN2Parallel(Body[] bodies, int dt, int numberOfBodies, int workers, Vector[] forces, int numSteps) {
        this.bodies = bodies;
        this.dt = dt;
        this.numberOfBodies = numberOfBodies;
        this.workers = workers;
        this.forces = forces;
        barrier = new CyclicBarrier(workers);
        this.numSteps = numSteps;
    }

    public void initiate(){
        SimulateN2Parallel simulation = new SimulateN2Parallel(workers, barrier, bodies, numberOfBodies, dt, forces, numSteps);
        int w = 0;
        Worker worker[] = new Worker[workers];
        for(int i = 0; i < workers; i++){
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
