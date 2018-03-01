import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class BHWorker extends Thread{
    QuadTree root;
    QuadTree myQuad;
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
        this.myQuad = myQuad;
        this.lock = lock;
        this.barrier = barrier;
        this.numSteps = numSteps;
    }

    public void run(){
       /* long t1, t2, t3;
        t1 = System.nanoTime();*/


        for (int step = 0; step < numSteps; step++) {
            myQuad.reset();
            myQuad.threadMagic(bodies, root, lock);
            System.out.println(root.aggregatedBodies.getMass());
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            for (int i = part; i < (part + (bodies.length / (double) workers)); i++) {
                System.out.println(bodies[i].getXPosition() + ":" + bodies[i].getYPosition());
                bodies[i].resetForce();
                if (bodies[i].inQuad(quad)) {
                    System.out.println(Thread.currentThread() + "i:" + i);

                    lock.lock();
                    root.calculateForce(bodies[i]);
                    bodies[i].update(dt);
                    lock.unlock();
                }
            }

            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
       /* t2 = System.nanoTime();
        t3 = t2 - t1;
        System.out.println(currentThread() +":"+ t3/1000000);*/

    }
}
