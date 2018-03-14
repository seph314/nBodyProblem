import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class SimulateN2Parallel {

    private int workers;
    private CyclicBarrier barrier;
    Body[] bodies;
    private int numberOfBodies;
    int dt;
    private Vector forces[];
    private int numSteps;

    /**
     * Constructor
     *
     * @param bodies is our array of bodies
     * @param dt     is the increase in time
     */
    SimulateN2Parallel(int workers, CyclicBarrier barrier, Body[] bodies, int numberOfBodies, int dt, Vector[] forces, int numSteps) {
        this.workers = workers;
        this.barrier = barrier;
        this.bodies = bodies;
        this.numberOfBodies = numberOfBodies;
        this.dt = dt;
        this.forces = forces;
        this.numSteps = numSteps;
    }

    /**
     * Simulates what happens to the bodies over time
     */
    void time(int w) throws InterruptedException {

        for (int step = 0; step < numSteps; step++) { // loops for each time step


            for (int i = w; i < (w + (numberOfBodies / workers)); i++) // each worker works on its own part of the body array
                for (int j = 0; j < numberOfBodies; j++) {
                    if (i != j)
                        forces[i] = forces[i].add(bodies[i].calculateForces(bodies[j])); // calculate an array of forces
                }

            try {
                barrier.await(); //Awaits all threads to finish force calculations before moving bodies.
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            for (int i = w; i < (w + (numberOfBodies / workers)); i++) {
                bodies[i].moveBodies(forces[i], dt); // move all bodies assigned to this thread.
            }

            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }
}
