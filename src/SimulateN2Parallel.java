import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SimulateN2Parallel {

    int workers;
    CyclicBarrier barrier;
    Body[] bodies;
    int numberOfBodies;
    int dt;
    Vector forces[];
    int numSteps;

    /**
     * Constructor
     *
     * @param bodies is our array of bodies
     * @param dt     is the increase in time
     */
    public SimulateN2Parallel(int workers, CyclicBarrier barrier, Body[] bodies, int numberOfBodies, int dt, Vector[] forces, int numSteps) {
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
    public void time(int w) throws InterruptedException {
        for(int step = 0; step < numSteps; step++){

            for (int i = w; i < (w+(numberOfBodies/workers)); i++)
                for (int j = 0; j < numberOfBodies; j++) {
                    if (i != j)
                        forces[i] = forces[i].add(bodies[i].calculateForces(bodies[j])); /* calculate an array of forces */
                }

                //Awaits all threads to finish force calculations before moving bodies.
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            // while (true){
                    //draw.setBodies(bodies);
                    //draw.keepDrawing();
                //new Draw(bodies);
            for (int i = w; i<(w+(numberOfBodies/workers)); i++) {
                bodies[i].movePoints(forces[i], dt); /* move all points */
            }

            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }
}
