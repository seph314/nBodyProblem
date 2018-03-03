import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class BHWorker extends Thread{
    QuadTree root;
    QuadTree myQuadTree;
    Quad quad;
    InitiateBarnesHutParallel iBHP;
    Body[] bodies;
    double dt;
    int part;
    int workers;
    ReentrantLock lock;
    CyclicBarrier barrier;
    int numSteps;

    public BHWorker(int numSteps, int workers, int part, double dt, Body[] bodies, QuadTree qT, Quad quad, QuadTree myQuad, ReentrantLock lock, CyclicBarrier barrier){
        this.root = qT;
        this.quad = quad;
        this.bodies = bodies;
        this.dt = dt;
        this.part = part;
        this.workers = workers;
        this.myQuadTree = myQuad;
        this.lock = lock;
        this.barrier = barrier;
        this.numSteps = numSteps;
    }
    public void run(){

        for(int i = part; i < bodies.length; i+= workers){
            bodies[i].resetForce();
            if (bodies[i].inQuad(quad)) {
                root.calculateForce(bodies[i]);
                bodies[i].update(dt);
            }
        }

    }
}



    /*public void run(){
        //for (int step = 0; step < numSteps; step++) {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            for (Body body : bodies) {
                if (body.inQuad(quad)) myQuadTree.build(body);

                lock.lock();
                if (root.aggregatedBodies == null)
                    root.aggregatedBodies = body;
                else
                    root.aggregatedBodies = root.aggregatedBodies.aggregate(body);
                lock.unlock();

            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
           / if(part == 0){
                addforces();
            }
        //}
    }


    private void addforces(){
        for (Body body : bodies) {
            body.resetForce();
            if (body.inQuad(quad)) {
                root.calculateForce(body);
                //Calculate the new positions on a time step dt (1e11 here)
                body.update(dt);
            }
        }
    }*/
    //}