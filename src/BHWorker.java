import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BHWorker extends Thread {
    private QuadTree root;
    private Body[] bodies;
    private double dt;
    private int part;
    private CyclicBarrier barrier;
    private int numSteps;
    private InitiateBarnesHutParallel init;
    private int numBodies;
    private int high;

    BHWorker(InitiateBarnesHutParallel init, int numSteps, int workers, int part, double dt, Body[] bodies, QuadTree qT, CyclicBarrier barrier) {
        this.root = qT;
        this.bodies = bodies;
        this.dt = dt;
        this.part = part;
        this.barrier = barrier;
        this.numSteps = numSteps;
        this.init = init;
        this.numBodies = bodies.length;
        this.high = part + (numBodies / workers);

    }

    public void run() {
        try {

            //Loops for as many steps given.
            for (int step = 0; step < numSteps; step++) {

                //Only one of the threads creates a new thread before new step.
                if (part == 0) {
                    init.newTree();
                    buildTree();
                }

                barrier.await();
                root = init.getShared();

                //Forces are calucalted in parallel with iterative parallelism. Every thread gets interavall to caluclate.
                for (int i = part; i < high; i++) {
                    bodies[i].resetForce();
                    root.calculateForce(bodies[i]);
                    bodies[i].update(dt);
                }
                barrier.await();
            }

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    //Builds tree
    private void buildTree() {
        for (int i = 0; i < numBodies; i++) {
            init.getShared().build(bodies[i]);
        }
    }

}