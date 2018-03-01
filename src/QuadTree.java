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


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Representation of a QuadTree
 */
public class QuadTree {

    /* a region that a tree represents */
    public Quad quad;

    /* a aggregatedBodies */
    public Body aggregatedBodies;

    /* child quadrants */
    public QuadTree northWest;
    public QuadTree northEast;
    public QuadTree southWest;
    public QuadTree southEast;

    /* theta */
    private double theta;// = 0.0;


    /**
     * QuadTree Constructor
     * Creates a QuadTree
     *
     * @param quad
     */
    QuadTree(Quad quad, double far) {
        this.quad = quad;
        this.aggregatedBodies = null;
        this.northWest = null;
        this.northEast = null;
        this.southWest = null;
        this.southEast = null;
        this.theta = far;
    }

    void reset(){
        this.aggregatedBodies = null;
        this.northWest = null;
        this.northEast = null;
        this.southWest = null;
        this.southEast = null;
    }

    public void threadQuads(Body[] bodies, int w, int dt,int numSteps){
        ReentrantLock lock = new ReentrantLock();
        System.out.println("w = " + w);
        CyclicBarrier barrier = new CyclicBarrier(w);
       // aggregatedBodies = new Body(new Vector(new double[]{0,0}),new Vector(new double[]{0,0}), 0);
        this.northWest = new QuadTree(quad.northEast(), theta);
        this.northEast = new QuadTree(quad.northWest(), theta);
        this.southWest = new QuadTree(quad.southWest(), theta);
        this.southEast = new QuadTree(quad.southEast(), theta);
        QuadTree[] qTArray = new QuadTree[]{northWest, northEast, southWest, southEast};
        String[] names = new String[]{"NW", "NE", "SW", "SE"};
       /* QuadWorker qWorker[] = new QuadWorker[w];
        qWorker[0] = new QuadWorker(northWest, bodies, this, lock);
        qWorker[1] = new QuadWorker(northEast, bodies, this, lock);
        qWorker[2] = new QuadWorker(southWest, bodies, this, lock);
        qWorker[3] = new QuadWorker(southEast, bodies, this, lock);
        qWorker[0].setName("NW");
        qWorker[1].setName("NE");
        qWorker[2].setName("SW");
        qWorker[3].setName("SE");*/
        int low = 0;
        BHWorker worker[] = new BHWorker[w];
        for (int i = 0; i < w; i++) {
            worker[i] = new BHWorker(numSteps, w, low, dt, bodies, this, quad, qTArray[i], lock, barrier);
            low += (bodies.length / w);
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
        //System.out.println("aggregatedBodies = " + aggregatedBodies.getMass());

    }

    public void threadMagic(Body[] bodies, QuadTree root, ReentrantLock lock){
        int n = bodies.length;
        for(int i = 0; i < n; i++){
           // System.out.println(Thread.currentThread() + "" + i);
            if (bodies[i].inQuad(quad)){
               // System.out.println(Thread.currentThread() + "" + i);
                build(bodies[i]);

                lock.lock();
                if(root.aggregatedBodies == null)
                    root.aggregatedBodies = bodies[i];
                else
                    root.aggregatedBodies = root.aggregatedBodies.aggregate(bodies[i]);
                lock.unlock();

            }

        }
    }

    /**
     * Insert aggregatedBodies in tree
     *
     * @param body is the aggregatedBodies we want to insert
     */
    public void build(Body body) {

        // if this node doesn't contain a aggregatedBodies, insert the new aggregatedBodies here
        if (aggregatedBodies == null) {
            aggregatedBodies = body;
           // System.out.println(Thread.currentThread() + "agg");
        }

        // if the body is internal: update the center of mass and total mass
        // recursively insert the body into the matching Quadrant
        else if (!external()) {
            aggregatedBodies = aggregatedBodies.aggregate(body); // aggregate the body if it's internal
            insert(body); // and insert it into the Tree
        }
        else if (external()) {

            insert(aggregatedBodies);
            insert(body);

        }
    }

    /**
     * A node is defined as external if it has no children
     *
     * @return true if external
     */
    private boolean external() {
        return northWest == null
                && northEast == null
                && southWest == null
                && southEast == null;
    }

    /**
     * Inserts the body into the correct Quadrant
     *
     * @param body is the body that we want to insert
     */
    private void insert(Body body) {
        if (body.inQuad(quad.northWest())) {
            if (this.northWest == null) {
                this.northWest = new QuadTree(quad.northWest(), theta);
            }
             northWest.build(body);
        }
        else {
            if (body.inQuad(quad.northEast())) {
                if (this.northEast == null) {
                    this.northEast = new QuadTree(quad.northEast(), theta);
                }
                northEast.build(body);
            }
            else {
                if (body.inQuad(quad.southWest())) {
                    if (this.southWest == null) {
                        this.southWest = new QuadTree(quad.southWest(), theta);
                    }
                    southWest.build(body);
                }
                else{
                    if (body.inQuad(quad.southEast())) {
                        if (this.southEast == null) {
                            this.southEast = new QuadTree(quad.southEast(), theta);
                        }
                        southEast.build(body);
                    }
                }
            }
        }
    }

    /**
     * Calculates the combined force the bodies in a QuadTree applies on a body
     * <p>
     * If the mass of an internal node is far away we treat the bodies in that part
     * of the tree as a single body
     * <p>
     * If the body is rather close, we calculate the force and check the children the same way
     * <p>
     * We calculate if a body is close enough by the ratio of the with of the internal nodes region
     * and the distance of the body and the nodes mass center.
     * <p>
     * This value is called Theta. Large Theta increases speed and smaller Theta accuracy.
     * (If Theta is 0, then no internal node is treated as a single body)
     */
    public void calculateForce(Body body) {

        // if the node is external we calculates the forces the aggregated bodies applies on this body
        // bodies do not apply forces on them selves ans not from null
        if (body == null || body.equals(aggregatedBodies))
            return;

        // if external: calculate the force applies to the body
        if (external()){
            //System.out.println(Thread.currentThread() + "aggregatedBodies = " + aggregatedBodies.getMass() + " Body" + body.getMass());
            body.addForce(aggregatedBodies);

        }


        else {
            // if the body is far enough away, treat all nodes under this internal as the same Body
            if (quad.getLength() / aggregatedBodies.calculateDistance(body) < theta) { //if this is true then the body is far away*/
                body.addForce(aggregatedBodies);
            }
            // else run the same thing on each of the internal nodes children
            else {
                if (northWest != null)
                    northWest.calculateForce(body);
                if (northEast != null)
                    northEast.calculateForce(body);
                if (southWest != null)
                    southWest.calculateForce(body);
                if (southEast != null)
                    southEast.calculateForce(body);
            }
        }
    }
}


