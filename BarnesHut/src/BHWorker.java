import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BHWorker extends Thread{
    QuadTree root;
    QuadTree myQuadTree;
    Quad quad;
    Body[] bodies;
    double dt;
    int part;
    int workers;
    ReentrantLock lock;
    CyclicBarrier barrier;
    int numSteps;
    Semaphore semaphore;

    public BHWorker(Semaphore semaphore, int numSteps, int workers, int part, double dt, Body[] bodies, QuadTree qT, Quad quad, QuadTree myQuad, ReentrantLock lock, CyclicBarrier barrier){
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
        this.semaphore = semaphore;
    }

    public void run(){
       /* long t1, t2, t3;
        t1 = System.nanoTime();*/


        for (int step = 0; step < numSteps; step++) {

            long t1, t2, t3;
            t1 = System.nanoTime();
            //myQuadTree.reset();
            //myQuadTree.threadMagic(bodies, root, lock);
            int n = bodies.length;
            for (Body body : bodies) {
                if (myQuadTree.quad.contains(body)){
                    System.out.println(Thread.currentThread());
                    myQuadTree.insert(body);}

                    lock.lock();
                    if(root.body == null)
                        root.body = body;
                    else
                        root.body = root.body.add(body);
                    lock.unlock();

                }

            t2 = System.nanoTime();
            t3 = t2 - t1;}
            /*
            System.out.println("Done1" +":"+ t3/10000);
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            for (int i = part; i < (part + (bodies.length / (double) workers)); i++) {
                // System.out.println(bodies[i].getXPosition() + ":" + bodies[i].getYPosition());
                bodies[i].resetForce();
                if (bodies[i].inQuad(quad)) {
//                   try {
//                        if(i == 0)
//                            Thread.sleep(100);
//                        if(i == 1)
//                            Thread.sleep(200);
//                        if(i == 2)
//                            Thread.sleep(300);
//                        if(i == 3)
//                            Thread.sleep(400);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    root.calculateForce(bodies[i]);
                    bodies[i].update(dt);
                    System.out.println(Thread.currentThread() + "i:" + i + "part" + part);

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

        //System.out.println(Thread.currentThread() + "" + " bodies = 0: " + bodies[0].getVelocity() + " 1: " + bodies[1].getXVelocity() + " 2: " + bodies[1].getXVelocity() + " 3: " + bodies[1].getXVelocity() + "" );
       /* t2 = System.nanoTime();
        t3 = t2 - t1;
        System.out.println(currentThread() +":"+ t3/1000000);*/

    }
}

