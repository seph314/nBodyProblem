import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BHWorker extends Thread{
    QuadTree root;
    Quad quad;
    Body[] bodies;
    double dt;
    int part;
    int workers;
    CyclicBarrier barrier;
    int numSteps;
    InitiateBarnesHutParallel init;
    int numBodies;
    int high;

    public BHWorker(InitiateBarnesHutParallel init, int numSteps, int workers, int part, double dt, Body[] bodies, QuadTree qT, Quad quad, CyclicBarrier barrier){
        this.root = qT;
        this.quad = quad;
        this.bodies = bodies;
        this.dt = dt;
        this.part = part;
        this.workers = workers;
        this.barrier = barrier;
        this.numSteps = numSteps;
        this.init = init;
        this.numBodies = bodies.length;
        this.high = part + (numBodies/workers);

    }
    public void run(){
        long t1, t2, t3 = 0;
        try {
        for (int step = 0; step < numSteps; step++) {
            t1 = System.nanoTime();
            if(part == 0){
                init.newTree();
               buildTree();
            }

            barrier.await();
            root = init.shared;

            for (int i = part; i < high; i++) {
            bodies[i].resetForce();
                root.calculateForce(bodies[i]);
                bodies[i].update(dt);
        }

            barrier.await();

           t2 = System.nanoTime();
            t3 = t2 - t1;
           // System.out.println("t3P - 1 -  = " + t3);

        }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void buildTree(){
        for (int i = 0; i < numBodies; i++) {
            init.shared.build(bodies[i]);
        }
    }

}