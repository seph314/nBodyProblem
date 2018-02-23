import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Simulate {

    int workers;
    CyclicBarrier barrier;
    Body[] bodies;
    int numberOfBodies;
    int dt;
    Vector forces[];

    /**
     * Constructor
     *
     * @param bodies is our array of bodies
     * @param dt     is the increase in time
     */
    public Simulate(int workers, CyclicBarrier barrier, Body[] bodies, int numberOfBodies, int dt, Vector[] forces) {
        this.workers = workers;
        this.barrier = barrier;
        this.bodies = bodies;
        this.numberOfBodies = numberOfBodies;
        this.dt = dt;
        this.forces = forces;
    }

    /**
     * Simulates what happens to the bodies over time
     */
    public void time(int w) throws InterruptedException {

        //Draw draw = new Draw(bodies);

        /*Vector[] forces = new Vector[bodies.length]; // a new force array with the length of tge bodies array
        for (int i = 0; i < bodies.length; i++) {
            forces[i] = new Vector(new double[2]);
        }*/
        for (int i = w; i < (w+(bodies.length/workers)); i++)
            for (int j = 0; j < bodies.length; j++) {
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
//                new Draw(bodies);
        for (int i = w; i<(w+(bodies.length/workers)); i++) {
            bodies[i].movePoints(forces[i], dt); /* move all points */
        }


                /*for (int i = w; i<(w+(bodies.length/workers)); i++) {
                    System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
                    System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
                    System.out.println("mass: " + bodies[i].getMass());
                }*/
            //}

//        new Draw(bodies); /* graphical representation of bodies */

    }
}
