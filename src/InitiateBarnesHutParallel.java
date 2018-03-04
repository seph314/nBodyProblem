/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * *
 * *    The Barnes-Hut algorithm assumes that the force from distant enough particles,
 * *    can be approximated with acceptable losses to accuracy by using their center mass and total mass.
 * *    This can reduce or runtime to n log(n).
 * *
 * *    We will use a quad tree (2D) structure to store the points/bodies in.
 * *
 * *    We will recursively divide the Bodies into groups in out Quad-Tree. Each node can has 4 children, they may be empty.
 * *    The first node represents all the space and this Nodes Children represents the first four Quadrants.
 * *    Each of these Quadrants can/will be subdivided into four new Quadrants and so on.
 * *    Each internal node will represent all the bodies beneath it in the tree. And each External node will
 * *    represent a body.
 * *
 * *    If we want to know the net force on a body, we can traverse the tree (from the root) and if we get to a internal body
 * *    that we think is far enough away that the accuracy is not suffering, we simply use that internal nodes aggravates properties
 * *    to calculate the force instead of all the bodies beneath it in the Tree.
 * *
 * *    With this Quad-tree we can calculate the total mass and it's center for each "box".
 * *    Traverse the tree for each particle to calculate the force. This parts is done in parallel with multiple threads.
 * *    Best case should be one core for each node.
 * *
 * *    Definitions:
 * *    External node (Leaf)
 * *    A node with no children
 * *
 * *    Internal node
 * *    A node with at least one child.
 * *
 * *    Each internal node represents a quadrant in space and can have up to four children
 * *    Each leaf/external node represents a point in space (or is empty)
 * *
 * * * * * * * * * * * * * * * * * * * *
 * *                                * *
 * *     B A R N E S - H U T       * *
 * *                              * *
 * * * * * * * * * * * * * * * * * */


import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class InitiateBarnesHutParallel {


    private Body[] bodies;
    private int dt;
    private double far;
    private int numSteps;
    private int workers;
    private CyclicBarrier barrier;
    Thread thread;
    double sizeOfTheUniverse;
    double[] startCoordinates;
    public QuadTree shared;
    private Quad quad;

    public InitiateBarnesHutParallel(Body[] bodies, int dt, double far, int numSteps, int numWorkers, double sizeOfTheUniverse,  double[] startCoordinates) {
        this.bodies = bodies;
        this.dt = dt;
        this.far = far;
        this.numSteps = numSteps;
        this.workers = numWorkers;
        barrier = new CyclicBarrier(numWorkers);
        this.sizeOfTheUniverse = sizeOfTheUniverse;
        this.startCoordinates = startCoordinates;
        Vector startVector = new Vector(startCoordinates);
        this.quad = new Quad(startVector, sizeOfTheUniverse);
        this.shared = new QuadTree(quad, far);
    }

    public void buildQuadTree() throws InterruptedException {
                addforces(quad);//}

    }

    public void addforces(Quad quad) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        CyclicBarrier barrier = new CyclicBarrier(workers);
        String[] names = new String[]{"NW", "NE", "SW", "SE"};
        int low = 0;
        BHWorker worker[] = new BHWorker[workers];
        for (int i = 0; i < workers; i++) {
            worker[i] = new BHWorker(this, numSteps, workers, low, dt, bodies, shared, quad, lock, barrier);
            low += (bodies.length / workers);
            worker[i].setName(names[i]);
            worker[i].start();
        }
        try {
            worker[0].join();
            worker[1].join();
            worker[2].join();
            worker[3].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
   public void newTree(){
       this.shared = new QuadTree(quad, far);
   }
}
