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


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

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

    public InitiateBarnesHutParallel(Body[] bodies, int dt, double far, int numSteps, int numWorkers, double sizeOfTheUniverse,  double[] startCoordinates) {
        this.bodies = bodies;
        this.dt = dt;
        this.far = far;
        this.numSteps = numSteps;
        this.workers = numWorkers;
        barrier = new CyclicBarrier(numWorkers);
        this.sizeOfTheUniverse = sizeOfTheUniverse;
        this.startCoordinates = startCoordinates;
    }

    public void buildQuadTree() throws InterruptedException {
        Vector startVector = new Vector(startCoordinates);

        /* create new Quad */
        Quad quad = new Quad(startVector, sizeOfTheUniverse);


        /* calculate forces */
        for (int i = 0; i < numSteps; i++)
            addforces(quad);

    }

    public void addforces(Quad quad) throws InterruptedException {
        QuadTree thetree = new QuadTree(quad, far);
        // If the body is still on the screen, add it to the tree
        /*for (int i = 0; i < bodies.length; i++) {
            if (bodies[i].inQuad(quad)) thetree.build(bodies[i]);
        }*/
        thetree.threadQuads(bodies, workers);
        System.out.println("mjao!");
        System.out.println("thetree NE= " + thetree.northEast.aggregatedBodies.getMass());
        System.out.println("thetree NW= " + thetree.northWest.aggregatedBodies.getMass());
        System.out.println("thetree SW= " + thetree.southWest.aggregatedBodies.getMass());
        System.out.println("thetree SE= " + thetree.southEast.aggregatedBodies.getMass());
        System.out.println("thetree OG= " + thetree.aggregatedBodies.getMass());
        thetree.aggregatedBodies = thetree.aggregatedBodies.aggregate(thetree.northEast.aggregatedBodies);
        thetree.aggregatedBodies = thetree.aggregatedBodies.aggregate(thetree.northWest.aggregatedBodies);
        thetree.aggregatedBodies = thetree.aggregatedBodies.aggregate(thetree.southWest.aggregatedBodies);
        thetree.aggregatedBodies = thetree.aggregatedBodies.aggregate(thetree.southEast.aggregatedBodies);

        long t1, t2, t3;
        t1 = System.nanoTime();
        int w = 0;
        workers = 1;
        BHWorker worker[] = new BHWorker[workers];
        for (int i = 0; i < workers; i++) {
            worker[i] = new BHWorker(workers, w, dt, bodies, thetree, quad);
            w += (bodies.length / workers);
            worker[i].start();
        }
       // System.out.println("worker = " + worker[0].part +" " +  worker[1].part +" " + worker[2].part +" " + worker[3].part);

        for (int i = 0; i < workers; i++) {
            worker[i].join();
        }
        workers =4;
        t2 = System.nanoTime();
        t3 = t2 - t1;
        System.out.println("Done" +":"+ t3/1000000);

        }

   /* public void forceMove(QuadTree qT, Quad quad){

        for (Body body : bodies) {
            body.resetForce();
            if (body.inQuad(quad)) {
                qT.calculateForce(body);
                body.update(dt);




        }
        }
    }*/
}
