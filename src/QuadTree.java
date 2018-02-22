/*
 * The Barnes-Hut algorithm assumes that the force from distant enough particles,
 * can be approximated with acceptable losses to accuracy by using their center mass and total mass.
 * This can reduce or runtime to n log(n).
 *
 * We will use a quad tree (2D) structure to store the points/bodies in.
 *
 * We will continue to divide into quadrants until there is only one particle in a leaf-node.
 *
 * With this Quad-tree we can calculate the total mass and it's center for each "box".
 * Traverse the tree for each particle to calculate the force. This parts is done in parallel with multiple threads.
 * Best case should be one core for each node.
 *
 * ,,,,,,,,,,,,,,,,,,,
 * B A R N E S - H U T
 * ```````````````````
 */

/**
 * Representation of a QuadTree
 */
public class QuadTree {

    /* we can choose any number we like as our node capacity,
     * this value tells us how many elements can be stores in tis quad tree node */
    private int nodeCapacity = 5;

    /* a region that a tree represents */
    private Quad quad;

    /* a body  */
    Body body = null;

    /* child quadrants */
    QuadTree northWest;
    QuadTree northEast;
    QuadTree southWest;
    QuadTree southEast;

    /**
     * Constructor
     * @param quad is a a region that a tree represents
     */
    QuadTree (Quad quad){
        this.quad = quad;
    }

//    public void buildQuadTree(Body[] bodies){
//        this.bodies = bodies;
//        for (Body body : bodies){
//            insert(body);
//        }
//    }

    /**
     * Insert a body if there currently is none
     * @param body
     */
    public void insert(Body body){
        if (this.body == null)
            this.body = body;
    }

}
